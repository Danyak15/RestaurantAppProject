package com.example.restaurantapp.domain.model

data class Reservation(
    val id: Long,
    val restaurantId: Int,
    val tableId: Long,
    val dateTime: String,
    val guests: Int,
    val status: String
)
