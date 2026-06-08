package com.example.restaurantapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class RestaurantEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val cuisine: String,
    val address: String,
    val description: String,
    val rating: Double,
    val phone: String?,
    val imageUrl: String
)