package com.example.restaurantapp.domain.model

data class Dish(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    var isFavorite: Boolean = false
)
