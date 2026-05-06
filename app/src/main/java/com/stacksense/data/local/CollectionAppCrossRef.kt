package com.stacksense.data.local

import androidx.room.Entity

@Entity(tableName = "collection_app_cross_ref", primaryKeys = ["collectionId", "packageName"])
data class CollectionAppCrossRef(
    val collectionId: Long,
    val packageName: String
)
