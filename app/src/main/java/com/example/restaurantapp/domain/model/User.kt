package com.example.restaurantapp.domain.model

data class User(
    val id: Long,
    val name: String,
    val surname: String,
    val phone: String,
    val email: String?,
    val loyaltyPoints: Int,
    val loyaltyLevel: String,
    val avatarUrl: String?
)
