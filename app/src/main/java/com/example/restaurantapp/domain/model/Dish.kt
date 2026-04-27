package com.example.restaurantapp.domain.model

data class Dish(
    val id: Int,
    val restaurantId: Int,
    val categoryId: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String
)
