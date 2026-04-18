package com.example.restaurantapp.data.remote.dto.request

data class RegisterRequest(
    val name: String,
    val surname: String,
    val email: String,
    val password: String
)