package com.example.restaurantapp.data.models

data class Restaurant(
    val id: Int,
    val name: String,
    val cuisine: String,
    val rating: Double,
    val imageUrl: String,
    val address: String,
    val description: String
)
