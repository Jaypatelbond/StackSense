package com.stacksense.data.repository

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.stacksense.data.analyzer.ApkAnalyzer
import com.stacksense.data.analyzer.LibrarySignatures
import com.stacksense.data.local.ScannedAppDao
import com.stacksense.data.local.ScannedAppEntity
import com.stacksense.data.model.AppInfo
import com.stacksense.data.model.Language
import com.stacksense.data.model.LibraryInfo
import com.stacksense.data.model.ScanProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for accessing and analyzing installed applications.
 * Uses Room database to cache scan results for faster subsequent loads.
 * All operations are performed offline using PackageManager and local APK analysis.
 */
@Singleton
class AppRepository @Inject constructor(
    private val packageManager: PackageManager,
    private val apkAnalyzer: ApkAnalyzer,
    private val scannedAppDao: ScannedAppDao
) {
    companion object {
        private const val TAG = "AppRepository"
    }

    /**
     * Gets a Flow of installed apps, observing database changes.
     * This ensures the UI updates automatically when an app is analyzed.
     */
    fun getInstalledAppsFlow(includeSystemApps: Boolean = false): Flow<List<AppInfo>> =
        scannedAppDao.getAllScannedApps()
            .map { cachedList ->
                val cachedMap = cachedList.associateBy { it.packageName }
                val installedApps = getInstalledApps(includeSystemApps)
                
                installedApps.map { app ->
                    val cached = cachedMap[app.packageName]
                    if (cached != null && cached.isValid(app.updateTime)) {
                        app.copy(
                            languages = parseLanguages(cached.languages),
                            libraries = parseLibraries(cached.libraries),
                            permissions = parsePermissions(cached.permissions),
                            minSdkVersion = cached.minSdkVersion,
                            targetSdkVersion = cached.targetSdkVersion,
                            isDebuggable = cached.isDebuggable,
                            installerPackageName = cached.installerPackageName,
                            isAnalyzed = true
                        )
                    } else {
                        app
                    }
                }
            }
            .flowOn(Dispatchers.IO)

    /**
     * Gets all installed apps, loading cached analysis results when available.
     * This provides instant results from cache for apps that haven't changed.
     */
    suspend fun getInstalledAppsWithCache(includeSystemApps: Boolean = false): List<AppInfo> =
        withContext(Dispatchers.IO) {
            val cachedApps = scannedAppDao.getAllScannedAppsOnce().associateBy { it.packageName }

            getInstalledApps(includeSystemApps).map { app ->
                val cached = cachedApps[app.packageName]
                if (cached != null && cached.isValid(app.updateTime)) {
                    // Use cached analysis results
                    app.copy(
                        languages = parseLanguages(cached.languages),
                        libraries = parseLibraries(cached.libraries),
                        permissions = parsePermissions(cached.permissions),
                        minSdkVersion = cached.minSdkVersion,
                        targetSdkVersion = cached.targetSdkVersion,
                        isDebuggable = cached.isDebuggable,
                        installerPackageName = cached.installerPackageName,
                        isAnalyzed = true
                    )
                } else {
                    app
                }
            }
        }

    /**
     * Gets all installed apps without analysis.
     * This is a quick operation for initial UI display.
     */
    fun getInstalledApps(includeSystemApps: Boolean = false): List<AppInfo> {
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            PackageManager.MATCH_UNINSTALLED_PACKAGES
        } else {
            @Suppress("DEPRECATION")
            PackageManager.GET_UNINSTALLED_PACKAGES
        }

        return try {
            packageManager.getInstalledApplications(flags)
                .filter { includeSystemApps || !isSystemApp(it) }
                .mapNotNull { appInfo -> createAppInfo(appInfo) }
                .sortedBy { it.appName.lowercase() }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting installed apps", e)
            emptyList()
        }
    }

    /**
     * Scans apps intelligently - only analyzes apps that:
     * 1. Haven't been scanned before
     * 2. Have been updated since last scan
     *
     * Returns cached results immediately for unchanged apps.
     */
    fun scanAppsOptimized(includeSystemApps: Boolean = false): Flow<Pair<ScanProgress, AppInfo?>> =
        flow {
            val apps = getInstalledApps(includeSystemApps)
            val cachedApps = scannedAppDao.getAllScannedAppsOnce().associateBy { it.packageName }
            val totalApps = apps.size
            var scanned = 0

            for (app in apps) {
                val progress = ScanProgress(
                    totalApps = totalApps,
                    scannedApps = scanned,
                    currentAppName = app.appName
                )
                emit(Pair(progress, null))

                val cached = cachedApps[app.packageName]
                val analyzedApp = if (cached != null && cached.isValid(app.updateTime)) {
                    // Use cached result - instant
                    app.copy(
                        languages = parseLanguages(cached.languages),
                        libraries = parseLibraries(cached.libraries),
                        permissions = parsePermissions(cached.permissions),
                        minSdkVersion = cached.minSdkVersion,
                        targetSdkVersion = cached.targetSdkVersion,
                        isDebuggable = cached.isDebuggable,
                        installerPackageName = cached.installerPackageName,
                        isAnalyzed = true
                    )
                } else {
                    // Need to analyze - takes time
                    val freshAnalysis = analyzeApp(app)
                    // Cache the result
                    cacheAppAnalysis(freshAnalysis)
                    freshAnalysis
                }

                scanned++
                val updatedProgress = progress.copy(scannedApps = scanned)
                emit(Pair(updatedProgress, analyzedApp))
            }

            // Clean up uninstalled apps from cache
            cleanupUninstalledApps(apps.map { it.packageName }.toSet())
        }.flowOn(Dispatchers.IO)

    /**
     * Force re-scan all apps, ignoring cache.
     */
    fun forceRescanAllApps(includeSystemApps: Boolean = false): Flow<Pair<ScanProgress, AppInfo?>> =
        flow {
            val apps = getInstalledApps(includeSystemApps)
            val totalApps = apps.size

            apps.forEachIndexed { index, app ->
                val progress = ScanProgress(
                    totalApps = totalApps,
                    scannedApps = index,
                    currentAppName = app.appName
                )
                emit(Pair(progress, null))

                val analyzedApp = analyzeApp(app)
                cacheAppAnalysis(analyzedApp)

                val updatedProgress = progress.copy(scannedApps = index + 1)
                emit(Pair(updatedProgress, analyzedApp))
            }
        }.flowOn(Dispatchers.IO)

    /**
     * Analyzes a single app.
     */
    suspend fun analyzeApp(app: AppInfo): AppInfo {
        return try {
            val (languages, libraries) = apkAnalyzer.analyze(app.apkPath)
            app.copy(
                languages = languages,
                libraries = libraries,
                isAnalyzed = true
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error analyzing ${app.packageName}", e)
            app.copy(isAnalyzed = true)
        }
    }

    /**
     * Gets a specific app by package name with cached analysis.
     */
    suspend fun getAppByPackage(packageName: String): AppInfo? = withContext(Dispatchers.IO) {
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            PackageManager.MATCH_UNINSTALLED_PACKAGES
        } else {
            @Suppress("DEPRECATION")
            PackageManager.GET_UNINSTALLED_PACKAGES
        }

        try {
            val applicationInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getApplicationInfo(
                    packageName,
                    PackageManager.ApplicationInfoFlags.of(flags.toLong())
                )
            } else {
                @Suppress("DEPRECATION")
                packageManager.getApplicationInfo(packageName, flags)
            }

            val app = createAppInfo(applicationInfo) ?: return@withContext null

            // Check cache first
            val cached = scannedAppDao.getScannedApp(packageName)
            if (cached != null && cached.isValid(app.updateTime)) {
                app.copy(
                    languages = parseLanguages(cached.languages),
                    libraries = parseLibraries(cached.libraries),
                    permissions = parsePermissions(cached.permissions),
                    minSdkVersion = cached.minSdkVersion,
                    targetSdkVersion = cached.targetSdkVersion,
                    isDebuggable = cached.isDebuggable,
                    installerPackageName = cached.installerPackageName,
                    isAnalyzed = true
                )
            } else {
                // Analyze and cache
                val analyzed = analyzeApp(app)
                cacheAppAnalysis(analyzed)
                analyzed
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting app: $packageName", e)
            null
        }
    }

    /**
     * Cache app analysis results.
     */
    private suspend fun cacheAppAnalysis(app: AppInfo) {
        try {
            val entity = ScannedAppEntity(
                packageName = app.packageName,
                appName = app.appName,
                versionName = app.versionName,
                versionCode = app.versionCode,
                apkPath = app.apkPath,
                apkSize = app.apkSize,
                installTime = app.installTime,
                updateTime = app.updateTime,
                isSystemApp = app.isSystemApp,
                minSdkVersion = app.minSdkVersion,
                targetSdkVersion = app.targetSdkVersion,
                isDebuggable = app.isDebuggable,
                installerPackageName = app.installerPackageName,
                languages = app.languages.joinToString("|||") { it.name },
                libraries = app.libraries.joinToString("|||") { it.name },
                permissions = app.permissions.joinToString("|||")
            )
            scannedAppDao.insertOrUpdate(entity)
        } catch (e: Exception) {
            Log.e(TAG, "Error caching app: ${app.packageName}", e)
        }
    }

    /**
     * Remove cache entries for uninstalled apps.
     */
    private suspend fun cleanupUninstalledApps(installedPackages: Set<String>) {
        try {
            val cachedApps = scannedAppDao.getAllScannedAppsOnce()
            for (cached in cachedApps) {
                if (cached.packageName !in installedPackages) {
                    scannedAppDao.delete(cached.packageName)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error cleaning up cache", e)
        }
    }

    /**
     * Clear all cached data.
     */
    suspend fun clearCache() {
        scannedAppDao.deleteAll()
    }

    /**
     * Parse language names from cached string.
     */
    private fun parseLanguages(cached: String): Set<Language> {
        if (cached.isEmpty()) return emptySet()
        return cached.split("|||").mapNotNull { name ->
            try {
                Language.valueOf(name)
            } catch (e: Exception) {
                null
            }
        }.toSet()
    }

    /**
     * Parse library names from cached string.
     */
    private fun parseLibraries(cached: String): List<LibraryInfo> {
        if (cached.isEmpty()) return emptyList()
        val names = cached.split("|||").toSet()
        return LibrarySignatures.SIGNATURES.values.filter { it.name in names }
    }

    /**
     * Parse permissions from cached string.
     */
    private fun parsePermissions(cached: String): List<String> {
        if (cached.isEmpty()) return emptyList()
        return cached.split("|||")
    }

    /**
     * Creates an AppInfo from ApplicationInfo.
     */
    private fun createAppInfo(applicationInfo: ApplicationInfo): AppInfo? {
        return try {
            val flags = PackageManager.GET_PERMISSIONS
            val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getPackageInfo(
                    applicationInfo.packageName,
                    PackageManager.PackageInfoFlags.of(flags.toLong())
                )
            } else {
                @Suppress("DEPRECATION")
                packageManager.getPackageInfo(applicationInfo.packageName, flags)
            }

            val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                @Suppress("DEPRECATION")
                packageInfo.versionCode.toLong()
            }

            val apkFile = java.io.File(applicationInfo.sourceDir)

            AppInfo(
                packageName = applicationInfo.packageName,
                appName = applicationInfo.loadLabel(packageManager).toString(),
                icon = applicationInfo.loadIcon(packageManager),
                versionName = packageInfo.versionName ?: "Unknown",
                versionCode = versionCode,
                apkPath = applicationInfo.sourceDir,
                apkSize = apkFile.length(),
                installTime = packageInfo.firstInstallTime,
                updateTime = packageInfo.lastUpdateTime,
                isSystemApp = isSystemApp(applicationInfo),
                minSdkVersion = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) applicationInfo.minSdkVersion else 0,
                targetSdkVersion = applicationInfo.targetSdkVersion,
                isDebuggable = (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0,
                installerPackageName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    try {
                        packageManager.getInstallSourceInfo(applicationInfo.packageName).installingPackageName
                    } catch (e: Exception) { null }
                } else {
                    @Suppress("DEPRECATION")
                    packageManager.getInstallerPackageName(applicationInfo.packageName)
                },
                permissions = packageInfo.requestedPermissions?.toList() ?: emptyList()
            )
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Checks if an app is a system app.
     */
    private fun isSystemApp(applicationInfo: ApplicationInfo): Boolean {
        return (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
    }
}
