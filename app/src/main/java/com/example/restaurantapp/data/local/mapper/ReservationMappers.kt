package com.example.restaurantapp.data.local.mapper

import com.example.restaurantapp.data.remote.dto.response.ReservationResponse
import com.example.restaurantapp.domain.model.Reservation

fun ReservationResponse.toDomain() = Reservation(
    id = id,
    restaurantId = restaurantId,
    tableId = tableId,
    dateTime = startTime,
    guests = guests,
    status = status
)