package com.stacksense.data.repository

import com.stacksense.data.local.FavoriteDao
import com.stacksense.data.local.FavoriteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepository @Inject constructor(
    private val favoriteDao: FavoriteDao
) {
    fun getFavorites(): Flow<List<String>> = favoriteDao.getFavorites()
    fun isFavorite(packageName: String): Flow<Boolean> = favoriteDao.isFavorite(packageName)
    suspend fun addFavorite(packageName: String) = favoriteDao.addFavorite(FavoriteEntity(packageName))
    suspend fun removeFavorite(packageName: String) = favoriteDao.removeFavorite(packageName)
}
