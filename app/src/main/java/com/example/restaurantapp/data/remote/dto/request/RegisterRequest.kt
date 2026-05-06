package com.example.restaurantapp.data.remote.dto.request

data class RegisterRequest(
    val name: String,
    val surname: String,
    val phone: String,
    val password: String
)