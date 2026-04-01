package com.stacksense.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StatsDao {
    @Query("SELECT COUNT(*) FROM scanned_apps")
    fun getTotalAnalyzedCount(): Flow<Int>

    @Query("SELECT languages FROM scanned_apps WHERE languages IS NOT NULL AND languages != ''")
    fun getAllLanguageStrings(): Flow<List<String>>

    @Query("SELECT libraries FROM scanned_apps WHERE libraries IS NOT NULL AND libraries != ''")
    fun getAllLibraryStrings(): Flow<List<String>>

    @Query("SELECT permissions FROM scanned_apps WHERE permissions IS NOT NULL AND permissions != ''")
    fun getAllPermissionStrings(): Flow<List<String>>

    @Query("SELECT AVG(apkSize) FROM scanned_apps")
    fun getAverageApkSize(): Flow<Long?>

    @Query("SELECT COUNT(*) FROM scanned_apps WHERE isDebuggable = 1")
    fun getDebuggableCount(): Flow<Int>

    @Query("SELECT MIN(minSdkVersion) FROM scanned_apps WHERE minSdkVersion > 0")
    fun getLowestMinSdk(): Flow<Int?>

    @Query("SELECT MAX(targetSdkVersion) FROM scanned_apps")
    fun getHighestTargetSdk(): Flow<Int?>
}
