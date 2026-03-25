package com.example.restaurantapp.domain.model

data class Restaurant(
    val id: Int,
    val name: String,
    val cuisine: String,
    val rating: Double,
    val address: String,
    val description: String
)