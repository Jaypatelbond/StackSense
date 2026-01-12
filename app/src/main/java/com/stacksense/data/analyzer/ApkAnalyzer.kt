package com.stacksense.data.analyzer

import android.util.Log
import com.stacksense.data.model.Language
import com.stacksense.data.model.LibraryInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.io.File
import java.util.zip.ZipFile
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Analyzes APK files to detect programming languages, cross-platform frameworks,
 * and third-party libraries.
 *
 * This analyzer works 100% offline by reading APK contents directly:
 * - Checks for Kotlin metadata in DEX files
 * - Detects native libraries (.so files)
 * - Identifies cross-platform frameworks by their signatures
 * - Scans DEX class listings for known library packages
 */
@Singleton
class ApkAnalyzer @Inject constructor() {

    companion object {
        private const val TAG = "ApkAnalyzer"

        // Cross-platform framework signatures
        private val FLUTTER_SIGNATURES = listOf(
            "libflutter.so",
            "flutter_assets/"
        )
        private val REACT_NATIVE_SIGNATURES = listOf(
            "libreactnativejni.so",
            "libreact_nativemodule",
            "assets/index.android.bundle",
            "com/facebook/react"
        )
        private val XAMARIN_SIGNATURES = listOf(
            "libmonodroid.so",
            "libmonosgen-2.0.so",
            "assemblies/"
        )
        // Cordova, Ionic, and Capacitor detection
        private val CORDOVA_SIGNATURES = listOf(
            "assets/www/cordova.js",
            "assets/www/index.html",
            "assets/www/cordova_plugins.js",
            "org/apache/cordova",
            // Ionic specific
            "assets/www/build/main.js",
            "assets/www/ionic.bundle.js",
            "io/ionic",
            // Capacitor (Ionic's modern alternative to Cordova)
            "com/getcapacitor",
            "assets/capacitor.config.json"
        )
        private val UNITY_SIGNATURES = listOf(
            "libunity.so",
            "libil2cpp.so",
            "assets/bin/Data/",
            "com/unity3d/player"
        )
        private val QT_SIGNATURES = listOf(
            "libQt5Core.so",
            "libQt6Core.so",
            "libqtforandroid.so"
        )
        // KMP detection needs to be very specific to avoid false positives
        // Only detect if we see actual KMP-specific artifacts
        private val KMP_SIGNATURES = listOf(
            "kotlin-stdlib-common",
            "kotlinx-coroutines-core-native",
            "kotlin-native"
        )
    }

    /**
     * Analyzes an APK file and returns detected languages and libraries.
     *
     * @param apkPath Path to the APK file
     * @return Pair of detected languages and libraries
     */
    suspend fun analyze(apkPath: String): Pair<Set<Language>, List<LibraryInfo>> =
        withContext(Dispatchers.IO) {
            val languages = mutableSetOf<Language>()
            // Thread-safe collections for parallel processing
            val detectedLibraries = java.util.Collections.synchronizedList(mutableListOf<LibraryInfo>())
            // Use ConcurrentHashMap to allow safe removal during iteration
            val remainingSignatures = java.util.concurrent.ConcurrentHashMap(LibrarySignatures.SIGNATURES)

            try {
                val apkFile = File(apkPath)
                if (!apkFile.exists() || !apkFile.canRead()) {
                    Log.w(TAG, "Cannot read APK: $apkPath")
                    return@withContext Pair(languages, emptyList())
                }

                ZipFile(apkFile).use { zip ->
                    val entries = zip.entries().toList()
                    val entryNames = entries.map { it.name }

                    // Detect languages and frameworks (fast, metadata only)
                    detectLanguagesAndFrameworks(entryNames, languages)

                    // Get all DEX files
                    val dexEntries = entries.filter { it.name.endsWith(".dex") }

                    // Process DEX files in parallel
                    // Use Default dispatcher for CPU-intensive string searching
                    // Process DEX files in parallel
                    // Use Default dispatcher for CPU-intensive string searching
                    withContext(Dispatchers.Default) {
                        dexEntries.map { dexEntry ->
                            async {
                                try {
                                    // Read DEX content as string
                                    // Note: This loads the file into memory, but it's necessary for searching.
                                    // bufferedReader handles encoding efficiently.
                                    val content = zip.getInputStream(dexEntry).bufferedReader(Charsets.ISO_8859_1).readText()

                                    // Iterate through remaining signatures
                                    // We use an iterator to safely remove found signatures
                                    val iterator = remainingSignatures.iterator()
                                    while (iterator.hasNext()) {
                                        val entry = iterator.next()
                                        val prefix = entry.key
                                        val libraryInfo = entry.value

                                        // Convert package prefix to DEX format: com.package -> Lcom/package/
                                        val dexPattern = "L${prefix.replace(".", "/")}/"

                                        if (content.contains(dexPattern)) {
                                            detectedLibraries.add(libraryInfo)
                                            // Optimization: Remove this signature so we don't look for it in other DEX files
                                            iterator.remove()
                                        }
                                    }
                                } catch (e: Exception) {
                                    Log.w(TAG, "Error scanning DEX: ${dexEntry.name}", e)
                                }
                            }
                        }.awaitAll() // Wait for all DEX files to be scanned
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error analyzing APK: $apkPath", e)
            }

            Pair(languages, detectedLibraries.distinct().sortedBy { it.name })
        }

    /**
     * Detects programming languages and cross-platform frameworks from APK entries.
     */
    private fun detectLanguagesAndFrameworks(
        entryNames: List<String>,
        languages: MutableSet<Language>
    ) {
        var hasKotlin = false
        var hasNative = false
        var hasFlutter = false
        var hasReactNative = false
        var hasXamarin = false
        var hasCordova = false
        var hasUnity = false
        var hasQt = false
        var hasKmp = false

        for (name in entryNames) {
            // Check for Kotlin
            if (!hasKotlin && (name.startsWith("kotlin/") || name.contains("kotlin/Metadata"))) {
                hasKotlin = true
            }

            // Check for native libs
            if (!hasNative && name.endsWith(".so") && name.contains("lib/")) {
                hasNative = true
            }

            // Check for Flutter
            if (!hasFlutter && FLUTTER_SIGNATURES.any { name.contains(it) }) {
                hasFlutter = true
            }

            // Check for React Native
            if (!hasReactNative && REACT_NATIVE_SIGNATURES.any { name.contains(it) }) {
                hasReactNative = true
            }

            // Check for Xamarin
            if (!hasXamarin && XAMARIN_SIGNATURES.any { name.contains(it) }) {
                hasXamarin = true
            }

            // Check for Cordova
            if (!hasCordova && CORDOVA_SIGNATURES.any { name.contains(it) }) {
                hasCordova = true
            }

            // Check for Unity
            if (!hasUnity && UNITY_SIGNATURES.any { name.contains(it) }) {
                hasUnity = true
            }

            // Check for Qt
            if (!hasQt && QT_SIGNATURES.any { name.contains(it) }) {
                hasQt = true
            }

            // Check for KMP
            if (!hasKmp && KMP_SIGNATURES.any { name.contains(it) }) {
                hasKmp = true
            }
        }

        // Add detected languages/frameworks
        // Cross-platform frameworks (mutually exclusive - pick the primary one)
        val hasCrossPlatform = hasFlutter || hasReactNative || hasXamarin || hasCordova || hasUnity || hasQt
        
        if (hasFlutter) languages.add(Language.FLUTTER)
        if (hasReactNative) languages.add(Language.REACT_NATIVE)
        if (hasXamarin) languages.add(Language.XAMARIN)
        if (hasCordova) languages.add(Language.CORDOVA)
        if (hasUnity) languages.add(Language.UNITY)
        if (hasQt) languages.add(Language.QT)
        
        // Only add KMP if no other cross-platform framework detected
        if (hasKmp && !hasCrossPlatform) languages.add(Language.KMP)
        if (hasKotlin) languages.add(Language.KOTLIN)
        if (hasNative && !hasFlutter && !hasReactNative && !hasXamarin && !hasUnity && !hasQt) {
            // Only add native if it's not from a cross-platform framework
            languages.add(Language.NATIVE)
        }

        // Add Java if no cross-platform framework detected (most Android apps use Java)
        if (languages.isEmpty() || (languages.size == 1 && languages.contains(Language.KOTLIN))) {
            languages.add(Language.JAVA)
        }
    }


}
