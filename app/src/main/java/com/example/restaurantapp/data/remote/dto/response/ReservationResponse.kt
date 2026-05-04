package com.example.restaurantapp.data.remote.dto.response

data class ReservationResponse(
    val id: Long,
    val restaurantId: Int,
    val tableId: Long,
    val startTime: String,
    val endTime: String,
    val guests: Int,
    val status: String,
)