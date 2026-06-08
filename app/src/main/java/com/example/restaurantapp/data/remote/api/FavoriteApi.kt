package com.example.restaurantapp.data.remote.api

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteApi {
    @GET("/api/favorites")
    suspend fun getFavoriteDishes(): Response<List<Long>>

    @POST("/api/favorites/{dishId}")
    suspend fun addToFavorites(@Path("dishId") dishId: Long): Response<Unit>

    @DELETE("/api/favorites/{dishId}")
    suspend fun removeFromFavorites(@Path("dishId") dishId: Long): Response<Unit>
}