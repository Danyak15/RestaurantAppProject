package com.example.restaurantapp.data.remote.api

import com.example.restaurantapp.data.remote.dto.response.SyncResponse
import retrofit2.Response
import retrofit2.http.GET

interface SyncApi {
    @GET("/api/sync")
    suspend fun sync(): Response<SyncResponse>
}