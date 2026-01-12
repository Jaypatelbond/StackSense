package com.stacksense.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Room database for StackSense app.
 * Caches scanned app data to avoid re-scanning on every launch.
 */
@Database(
    entities = [ScannedAppEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class StackSenseDatabase : RoomDatabase() {
    abstract fun scannedAppDao(): ScannedAppDao
    
    companion object {
        const val DATABASE_NAME = "stacksense_db"
    }
}
