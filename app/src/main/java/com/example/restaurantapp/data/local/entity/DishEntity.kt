package com.example.restaurantapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dishes")
data class DishEntity(
    @PrimaryKey val id: Long,
    val restaurantId: Long,
    val categoryId: Long,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    val displayOrder: Int,
    val imageUrl: String
)
