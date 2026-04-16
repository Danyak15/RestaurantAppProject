package com.example.restaurantapp.data.remote.api

import com.example.restaurantapp.data.remote.dto.LoginRequest
import com.example.restaurantapp.data.remote.dto.LoginResponse
import com.example.restaurantapp.data.remote.dto.RegisterRequest
import com.example.restaurantapp.data.remote.dto.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("users/me")
    suspend fun getMe(@Header("Authorization") authHeader: String): Response<UserResponse>
}