package com.example.restaurantapp.domain.model

data class MenuItem(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String
)
