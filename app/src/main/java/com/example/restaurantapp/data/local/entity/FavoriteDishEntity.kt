package com.example.restaurantapp.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "favorite_dishes",
    primaryKeys = ["userId", "dishId"]
)
data class FavoriteDishEntity(
    val userId: Long,
    val dishId: Long
)
