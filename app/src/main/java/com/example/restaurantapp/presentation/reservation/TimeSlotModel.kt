package com.example.restaurantapp.presentation.reservation

import java.time.LocalTime

data class TimeSlotModel(
    val time: LocalTime,
    val isSelected: Boolean = false
)
