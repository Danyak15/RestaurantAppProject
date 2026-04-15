package com.example.restaurantapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dishes")
data class DishEntity(
    @PrimaryKey val id: Int,
    val categoryId: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    var isFavorite: Boolean = false
)
