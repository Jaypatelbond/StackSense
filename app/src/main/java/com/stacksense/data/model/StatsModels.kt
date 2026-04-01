package com.stacksense.data.model

data class LanguageStats(
    val language: Language,
    val appCount: Int,
    val percentage: Float
)

data class LibraryStats(
    val libraryName: String,
    val category: LibraryCategory,
    val appCount: Int
)

data class CategoryDistribution(
    val category: LibraryCategory,
    val libraryCount: Int,
    val percentage: Float
)

data class OverallStats(
    val totalApps: Int,
    val analyzedApps: Int,
    val totalLibraries: Int,
    val totalPermissions: Int,
    val averageLibrariesPerApp: Float,
    val averagePermissionsPerApp: Float,
    val mostCommonLanguage: Language?,
    val mostCommonLibrary: String?,
    val languageDistribution: List<LanguageStats>,
    val topLibraries: List<LibraryStats>,
    val categoryDistribution: List<CategoryDistribution>
)
