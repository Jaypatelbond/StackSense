package com.stacksense.data.model

data class ComparisonResult(
    val apps: List<AppInfo>,
    val commonLanguages: Set<Language>,
    val uniqueLanguages: Map<String, Set<Language>>,
    val commonLibraries: List<LibraryInfo>,
    val uniqueLibraries: Map<String, List<LibraryInfo>>,
    val commonPermissions: List<String>,
    val uniquePermissions: Map<String, List<String>>,
    val sizeComparison: Map<String, Long>
)
