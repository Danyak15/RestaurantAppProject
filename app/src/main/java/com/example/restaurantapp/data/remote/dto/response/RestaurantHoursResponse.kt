package com.example.restaurantapp.data.remote.dto.response

data class RestaurantHoursResponse(
    val dayOfWeek: String,
    val openTime: String?,
    val closeTime: String?,
    val isClosed: Boolean
)
