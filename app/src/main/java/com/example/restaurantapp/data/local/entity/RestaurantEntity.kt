package com.example.restaurantapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "restaurants")
data class RestaurantEntity(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val cuisine: String,
    val rating: Double,
    val address: String,
    val description: String
)