package com.example.restaurantapp.domain.model

import java.time.DayOfWeek
import java.time.LocalTime

data class RestaurantWorkingHours(
    val dayOfWeek: DayOfWeek,
    val openTime: LocalTime?,
    val closeTime: LocalTime?,
    val isClosed: Boolean
)
