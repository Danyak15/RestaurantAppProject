package com.example.restaurantapp.data.remote.dto.response

data class CategoryResponse(
    val id: Long,
    val restaurantId: Long,
    val name: String,
    val displayOrder: Int
)
