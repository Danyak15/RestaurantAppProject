package com.example.restaurantapp.data.remote.dto.response

data class UserResponse(
    val id: Long,
    val name: String,
    val surname: String,
    val phone: String,
    val email: String?
)