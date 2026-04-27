package com.example.restaurantapp.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "favorite_dishes",
    primaryKeys = ["userEmail", "dishId"]
)
data class FavoriteDishEntity(
    val userEmail: String,
    val dishId: Int
)
