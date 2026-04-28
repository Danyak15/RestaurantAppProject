package com.example.restaurantapp.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "favorite_sync_queue",
    primaryKeys = ["userEmail", "dishId"]
)
data class FavoriteSyncEntity(
    val userEmail: String,
    val dishId: Int,
    val action: String
)
