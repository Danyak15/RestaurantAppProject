package com.example.restaurantapp.data.remote.dto.response

data class NewsResponse(
    val id: Long,
    val restaurantId: Long?,
    val title: String,
    val content: String,
    val createdAt: String
)
