package com.stacksense.data.model

data class SearchFilter(
    val query: String = "",
    val language: Language? = null,
    val category: LibraryCategory? = null,
    val isSystemApp: Boolean? = null,
    val isDebuggable: Boolean? = null,
    val minSdk: Int = 0,
    val targetSdk: Int = 0
)
