package com.stacksense.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for scanned apps.
 */
@Dao
interface ScannedAppDao {
    
    /**
     * Get all cached scanned apps.
     */
    @Query("SELECT * FROM scanned_apps ORDER BY appName ASC")
    fun getAllScannedApps(): Flow<List<ScannedAppEntity>>
    
    /**
     * Get all cached scanned apps (one-shot).
     */
    @Query("SELECT * FROM scanned_apps ORDER BY appName ASC")
    suspend fun getAllScannedAppsOnce(): List<ScannedAppEntity>
    
    /**
     * Get a specific scanned app by package name.
     */
    @Query("SELECT * FROM scanned_apps WHERE packageName = :packageName")
    suspend fun getScannedApp(packageName: String): ScannedAppEntity?
    
    /**
     * Insert or update a scanned app.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(app: ScannedAppEntity)
    
    /**
     * Insert or update multiple scanned apps.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAll(apps: List<ScannedAppEntity>)
    
    /**
     * Delete a scanned app (when uninstalled).
     */
    @Query("DELETE FROM scanned_apps WHERE packageName = :packageName")
    suspend fun delete(packageName: String)
    
    /**
     * Delete all cached data.
     */
    @Query("DELETE FROM scanned_apps")
    suspend fun deleteAll()
    
    /**
     * Get count of cached apps.
     */
    @Query("SELECT COUNT(*) FROM scanned_apps")
    suspend fun getCount(): Int
    
    /**
     * Check if an app is cached.
     */
    @Query("SELECT EXISTS(SELECT 1 FROM scanned_apps WHERE packageName = :packageName)")
    suspend fun exists(packageName: String): Boolean
}
