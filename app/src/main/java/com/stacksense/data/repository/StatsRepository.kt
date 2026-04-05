package com.stacksense.data.repository

import com.stacksense.data.local.StatsDao
import com.stacksense.data.model.*
import com.stacksense.data.analyzer.LibrarySignatures
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatsRepository @Inject constructor(
    private val statsDao: StatsDao
) {
    private val statsCache = ConcurrentHashMap<String, OverallStats>()
    fun getOverallStats(): Flow<OverallStats> {
        return combine(
            statsDao.getTotalAnalyzedCount(),
            statsDao.getAllLanguageStrings(),
            statsDao.getAllLibraryStrings(),
            statsDao.getAllPermissionStrings()
        ) { totalAnalyzed, languageStrings, libraryStrings, permissionStrings ->
            computeOverallStats(totalAnalyzed, languageStrings, libraryStrings, permissionStrings)
        }
    }

    private fun computeOverallStats(
        totalAnalyzed: Int,
        languageStrings: List<String>,
        libraryStrings: List<String>,
        permissionStrings: List<String>
    ): OverallStats {
        val cacheKey = "${totalAnalyzed}_${languageStrings.hashCode()}_${libraryStrings.hashCode()}_${permissionStrings.hashCode()}"
        statsCache[cacheKey]?.let { return it }
        val languageCounts = mutableMapOf<Language, Int>()
        languageStrings.forEach { langStr ->
            langStr.split("|||").filter { it.isNotBlank() }.forEach { name ->
                try {
                    val lang = Language.valueOf(name)
                    languageCounts[lang] = (languageCounts[lang] ?: 0) + 1
                } catch (e: Exception) {}
            }
        }
        val languageDistribution = languageCounts.map { (lang, count) ->
            LanguageStats(
                language = lang,
                appCount = count,
                percentage = if (totalAnalyzed > 0) count.toFloat() / totalAnalyzed * 100f else 0f
            )
        }.sortedByDescending { it.appCount }

        val libraryCounts = mutableMapOf<String, Int>()
        libraryStrings.forEach { libStr ->
            libStr.split("|||").filter { it.isNotBlank() }.forEach { name ->
                libraryCounts[name] = (libraryCounts[name] ?: 0) + 1
            }
        }

        val topLibraries = libraryCounts.entries
            .sortedByDescending { it.value }
            .take(15)
            .map { (name, count) ->
                val category = LibrarySignatures.SIGNATURES.values.firstOrNull { it.name == name }?.category ?: LibraryCategory.UTILITIES
                LibraryStats(
                    libraryName = name,
                    category = category,
                    appCount = count
                )
            }

        val categoryCounts = mutableMapOf<LibraryCategory, Int>()
        libraryCounts.forEach { (name, count) ->
            val category = LibrarySignatures.SIGNATURES.values.firstOrNull { it.name == name }?.category ?: LibraryCategory.UTILITIES
            categoryCounts[category] = (categoryCounts[category] ?: 0) + count
        }

        val totalLibs = categoryCounts.values.sum()
        val categoryDistribution = categoryCounts.map { (cat, count) ->
            CategoryDistribution(
                category = cat,
                libraryCount = count,
                percentage = if (totalLibs > 0) count.toFloat() / totalLibs * 100f else 0f
            )
        }.sortedByDescending { it.libraryCount }

        val totalPermissions = permissionStrings.sumOf { it.split("|||").count { p -> p.isNotBlank() } }
        val avgLibs = if (totalAnalyzed > 0) libraryStrings.sumOf { it.split("|||").count { l -> l.isNotBlank() } }.toFloat() / totalAnalyzed else 0f
        val avgPerms = if (totalAnalyzed > 0) totalPermissions.toFloat() / totalAnalyzed else 0f

        val overallResult = OverallStats(
            totalApps = totalAnalyzed,
            analyzedApps = totalAnalyzed,
            totalLibraries = libraryCounts.size,
            totalPermissions = totalPermissions,
            averageLibrariesPerApp = avgLibs,
            averagePermissionsPerApp = avgPerms,
            mostCommonLanguage = languageDistribution.firstOrNull()?.language,
            mostCommonLibrary = topLibraries.firstOrNull()?.libraryName,
            languageDistribution = languageDistribution,
            topLibraries = topLibraries,
            categoryDistribution = categoryDistribution
        )
        statsCache[cacheKey] = overallResult
        return overallResult
    }
}
