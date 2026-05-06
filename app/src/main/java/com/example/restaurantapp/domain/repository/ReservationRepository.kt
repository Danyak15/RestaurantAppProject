package com.example.restaurantapp.domain.repository

import com.example.restaurantapp.domain.model.Reservation

interface ReservationRepository {
    suspend fun getMyReservations(): Result<List<Reservation>>

    suspend fun createReservation(
        restaurantId: Int,
        dateTime: String,
        guests: Int
    ): Result<Reservation>

    suspend fun cancelReservation(id: Long): Result<Reservation>

    suspend fun getAvailableTimes(
        restaurantId: Int,
        date: String,
        guests: Int
    ): Result<List<String>>
}