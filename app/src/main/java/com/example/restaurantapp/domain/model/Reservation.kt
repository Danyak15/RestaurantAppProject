package com.example.restaurantapp.domain.model

data class Reservation(
    val id: Long,
    val restaurantId: Long,
    val tableId: Long,
    val dateTime: String,
    val guests: Int,
    val status: String
)
