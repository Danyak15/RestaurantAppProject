package com.example.restaurantapp.data.remote.api

import retrofit2.http.Query
import com.example.restaurantapp.data.remote.dto.request.ReservationRequest
import com.example.restaurantapp.data.remote.dto.response.ReservationResponse
import com.example.restaurantapp.data.remote.dto.response.TimeSlotResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReservationApi {
    @GET("/api/reservations/me")
    suspend fun getMyReservations(): Response<List<ReservationResponse>>

    @POST("/api/reservations")
    suspend fun createReservation(@Body request: ReservationRequest): Response<ReservationResponse>

    @DELETE("/api/reservations/{id}")
    suspend fun cancelReservation(@Path("id") id: Long): Response<ReservationResponse>

    @GET("/api/reservations/available-times")
    suspend fun getAvailableTimes(
        @Query("restaurantId") restaurantId: Long,
        @Query("date") date: String,
        @Query("guests") guests: Int
    ): Response<List<TimeSlotResponse>>
}