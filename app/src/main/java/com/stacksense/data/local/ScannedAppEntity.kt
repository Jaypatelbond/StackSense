package com.stacksense.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

/**
 * Room entity for caching analyzed app data.
 * Stores scan results to avoid re-scanning on every app launch.
 */
@Entity(tableName = "scanned_apps")
@TypeConverters(Converters::class)
data class ScannedAppEntity(
    @PrimaryKey
    val packageName: String,
    val appName: String,
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
    val languages: String, // JSON array of language names
    val libraries: String, // JSON array of library names
    val permissions: String, // JSON array of permission names
    val lastScannedAt: Long = System.currentTimeMillis()
) {
    /**
     * Check if this cached entry is still valid.
     * Re-scan if app was updated after last scan.
     */
    fun isValid(currentUpdateTime: Long): Boolean {
        return updateTime == currentUpdateTime
    }
}

/**
 * Type converters for Room.
 */
class Converters {
    @TypeConverter
    fun fromStringList(value: String): List<String> {
        return if (value.isEmpty()) emptyList()
        else value.split("|||")
    }

    @TypeConverter
    fun toStringList(list: List<String>): String {
        return list.joinToString("|||")
    }
}
