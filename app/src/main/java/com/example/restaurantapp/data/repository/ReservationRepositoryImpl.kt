package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.local.mapper.toDomain
import com.example.restaurantapp.data.remote.api.ReservationApi
import com.example.restaurantapp.data.remote.dto.request.ReservationRequest
import com.example.restaurantapp.data.utils.NetworkHelper
import com.example.restaurantapp.domain.model.Reservation
import com.example.restaurantapp.domain.repository.ReservationRepository
import javax.inject.Inject

class ReservationRepositoryImpl @Inject constructor(
    private val reservationApi: ReservationApi,
    private val networkHelper: NetworkHelper
) : ReservationRepository {
    override suspend fun getMyReservations(): Result<List<Reservation>> {
        return try {
            networkHelper.checkInternetConnection()

            val response = reservationApi.getMyReservations()

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.map { it.toDomain() })
            } else {
                Result.failure(Exception("Ошибка при получении бронирований"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createReservation(
        restaurantId: Int,
        dateTime: String,
        guests: Int
    ): Result<Reservation> {
        return try {
            networkHelper.checkInternetConnection()

            val response = reservationApi.createReservation(
                ReservationRequest(
                    restaurantId = restaurantId,
                    dateTime = dateTime,
                    guests = guests
                )
            )

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.toDomain())
            } else {
                Result.failure(Exception("Ошибка при бронировании столика"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun cancelReservation(id: Long): Result<Reservation> {
        return try {
            networkHelper.checkInternetConnection()

            val response = reservationApi.cancelReservation(id)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.toDomain())
            } else {
                Result.failure(Exception("Ошибка при отмене брони"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAvailableTimes(
        restaurantId: Int,
        date: String,
        guests: Int
    ): Result<List<String>> {
        return try {
            networkHelper.checkInternetConnection()

            val response = reservationApi.getAvailableTimes(
                restaurantId = restaurantId,
                date = date,
                guests = guests
            )

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.map { it.time })
            } else {
                Result.failure(Exception("Ошибка при загруке доступного времени: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}