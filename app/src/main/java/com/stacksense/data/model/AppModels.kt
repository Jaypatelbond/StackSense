package com.stacksense.data.model

import android.graphics.drawable.Drawable

/**
 * Represents programming/platform languages detected in an app.
 */
enum class Language(val displayName: String, val color: Long) {
    KOTLIN("Kotlin", 0xFF7F52FF),      // Kotlin purple
    JAVA("Java", 0xFFED8B00),           // Java orange
    NATIVE("Native (C/C++)", 0xFF00599C), // C++ blue
    FLUTTER("Flutter", 0xFF02569B),     // Flutter blue
    REACT_NATIVE("React Native", 0xFF61DAFB), // React cyan
    XAMARIN("Xamarin", 0xFF3498DB),     // Xamarin blue
    CORDOVA("Cordova/Ionic", 0xFF4880EE), // Cordova blue
    UNITY("Unity", 0xFF000000),          // Unity black
    QT("Qt", 0xFF41CD52),                // Qt green
    KMP("Kotlin Multiplatform", 0xFFE24462) // KMP red-ish
}

/**
 * Represents a category of third-party libraries.
 */
enum class LibraryCategory(val displayName: String, val color: Long) {
    NETWORKING("Networking", 0xFF4CAF50),
    IMAGE_LOADING("Image Loading", 0xFF2196F3),
    DEPENDENCY_INJECTION("Dependency Injection", 0xFFFF9800),
    DATABASE("Database", 0xFF9C27B0),
    REACTIVE("Reactive Programming", 0xFFE91E63),
    ANALYTICS("Analytics", 0xFF607D8B),
    ADVERTISING("Advertising", 0xFFFF5722),
    UI("UI Components", 0xFF00BCD4),
    SERIALIZATION("Serialization", 0xFF795548),
    TESTING("Testing", 0xFF8BC34A),
    SECURITY("Security", 0xFFF44336),
    SOCIAL("Social", 0xFF3F51B5),
    UTILITIES("Utilities", 0xFF9E9E9E),
    CRASH_REPORTING("Crash Reporting", 0xFFCDDC39),
    LOGGING("Logging", 0xFF673AB7),
    ARCHITECTURE("Architecture", 0xFF009688),
    MAPS("Maps & Location", 0xFF4285F4),
    MEDIA("Media & Video", 0xFFE040FB),
    PUSH_NOTIFICATIONS("Push Notifications", 0xFFFF6F00),
    PAYMENTS("Payments", 0xFF1B5E20),
    DEEP_LINKING("Deep Linking", 0xFF7C4DFF),
    STORAGE("Storage & Files", 0xFF5D4037),
    REACT_NATIVE("React Native", 0xFF61DAFB),
    FIREBASE("Firebase", 0xFFFFA000),
    MACHINE_LEARNING("Machine Learning", 0xFF00897B),
    BLUETOOTH("Bluetooth & IoT", 0xFF1565C0),
    CAMERA("Camera & Scanner", 0xFF6D4C41)
}

/**
 * Represents a detected third-party library.
 */
data class LibraryInfo(
    val name: String,
    val category: LibraryCategory,
    val packagePrefix: String,
    val description: String = "",
    val website: String = ""
)

/**
 * Represents an installed application with its analysis results.
 */
data class AppInfo(
    val packageName: String,
    val appName: String,
    val icon: Drawable?,
    val versionName: String,
    val versionCode: Long,
    val apkPath: String,
    val apkSize: Long,
    val installTime: Long,
    val updateTime: Long,
    val isSystemApp: Boolean,
    val minSdkVersion: Int = 0,
    val targetSdkVersion: Int = 0,
    val isDebuggable: Boolean = false,
    val installerPackageName: String? = null,
    val languages: Set<Language> = emptySet(),
    val libraries: List<LibraryInfo> = emptyList(),
    val permissions: List<String> = emptyList(),
    val isAnalyzed: Boolean = false
)

/**
 * Represents the analysis state of an app.
 */
sealed class AnalysisState {
    data object NotStarted : AnalysisState()
    data object InProgress : AnalysisState()
    data class Completed(val appInfo: AppInfo) : AnalysisState()
    data class Error(val message: String) : AnalysisState()
}

/**
 * Represents overall scanning progress.
 */
data class ScanProgress(
    val totalApps: Int,
    val scannedApps: Int,
    val currentAppName: String
) {
    val progress: Float
        get() = if (totalApps > 0) scannedApps.toFloat() / totalApps else 0f
    
    val isComplete: Boolean
        get() = scannedApps >= totalApps
}
