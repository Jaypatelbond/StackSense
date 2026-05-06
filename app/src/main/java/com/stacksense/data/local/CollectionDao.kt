package com.stacksense.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {
    @Query("SELECT * FROM collections")
    fun getAllCollections(): Flow<List<CollectionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollection(collection: CollectionEntity): Long

    @Query("DELETE FROM collections WHERE id = :id")
    suspend fun deleteCollection(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAppToCollection(crossRef: CollectionAppCrossRef)

    @Query("DELETE FROM collection_app_cross_ref WHERE collectionId = :collectionId AND packageName = :packageName")
    suspend fun removeAppFromCollection(collectionId: Long, packageName: String)

    @Query("SELECT packageName FROM collection_app_cross_ref WHERE collectionId = :collectionId")
    fun getAppsInCollection(collectionId: Long): Flow<List<String>>
}
