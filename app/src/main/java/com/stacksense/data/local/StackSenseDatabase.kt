package com.stacksense.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ScannedAppEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class StackSenseDatabase : RoomDatabase() {
    abstract fun scannedAppDao(): ScannedAppDao
    abstract fun statsDao(): StatsDao
    
    companion object {
        const val DATABASE_NAME = "stacksense_db"
    }
}
