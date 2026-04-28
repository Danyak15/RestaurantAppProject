package com.example.restaurantapp.data.remote.api

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteApi {
    @GET("favorites")
    suspend fun getFavoriteDishes(
        @Header("Authorization") authHeader: String
    ): Response<List<Int>>

    @POST("favorites/{dishId}")
    suspend fun addToFavorites(
        @Header("Authorization") authHeader: String,
        @Path("dishId") dishId: Int
    ): Response<Unit>

    @DELETE("favorites/{dishId}")
    suspend fun removeFromFavorites(
        @Header("Authorization") authHeader: String,
        @Path("dishId") dishId: Int
    ): Response<Unit>
}