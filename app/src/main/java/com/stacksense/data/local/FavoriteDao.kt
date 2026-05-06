package com.stacksense.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT packageName FROM favorites")
    fun getFavorites(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE packageName = :packageName")
    suspend fun removeFavorite(packageName: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE packageName = :packageName)")
    fun isFavorite(packageName: String): Flow<Boolean>
}
