package com.stacksense.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        ScannedAppEntity::class,
        FavoriteEntity::class,
        CollectionEntity::class,
        CollectionAppCrossRef::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class StackSenseDatabase : RoomDatabase() {
    abstract fun scannedAppDao(): ScannedAppDao
    abstract fun statsDao(): StatsDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun collectionDao(): CollectionDao
    
    companion object {
        const val DATABASE_NAME = "stacksense_db"
    }
}
