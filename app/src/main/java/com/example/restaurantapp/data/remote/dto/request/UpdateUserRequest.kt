package com.example.restaurantapp.data.remote.dto.request

data class UpdateUserRequest(
    val name: String,
    val surname: String,
    val email: String? = null
)