package com.example.restaurantapp.data.remote.dto.response

data class LoginResponse(
    val token: String,
    val user: UserResponse
)