package com.example.restaurantapp.domain.model

data class Restaurant(
    val id: Long,
    val name: String,
    val cuisine: String,
    val address: String,
    val description: String,
    val rating: Double,
    val phone: String?,
    val imageUrl: String,
    val workingHours: List<RestaurantWorkingHours>
)
