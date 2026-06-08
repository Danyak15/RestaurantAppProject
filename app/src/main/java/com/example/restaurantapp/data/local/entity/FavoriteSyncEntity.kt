package com.example.restaurantapp.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "favorite_sync_queue",
    primaryKeys = ["userId", "dishId"]
)
data class FavoriteSyncEntity(
    val userId: Long,
    val dishId: Long,
    val action: String
)
