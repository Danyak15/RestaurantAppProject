package com.example.restaurantapp.data.remote.dto.request

data class ReservationRequest(
    val restaurantId: Long,
    val dateTime: String,
    val guests: Int
)
