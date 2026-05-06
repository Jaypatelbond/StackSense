package com.stacksense.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val packageName: String,
    val addedTime: Long = System.currentTimeMillis()
)
