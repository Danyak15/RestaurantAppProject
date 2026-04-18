package com.example.restaurantapp.data.remote.api

import com.example.restaurantapp.data.remote.dto.request.LoginRequest
import com.example.restaurantapp.data.remote.dto.response.LoginResponse
import com.example.restaurantapp.data.remote.dto.request.RegisterRequest
import com.example.restaurantapp.data.remote.dto.response.UserResponse
import com.example.restaurantapp.data.remote.dto.request.UpdateUserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("users/me")
    suspend fun getMe(@Header("Authorization") authHeader: String): Response<UserResponse>

    @PUT("users/me")
    suspend fun updateMe(
        @Header("Authorization") authHeader: String,
        @Body request: UpdateUserRequest
    ): Response<UserResponse>
}