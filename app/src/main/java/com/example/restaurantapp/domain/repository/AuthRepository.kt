package com.example.restaurantapp.domain.repository

import com.example.restaurantapp.data.remote.dto.LoginRequest
import com.example.restaurantapp.data.remote.dto.LoginResponse
import com.example.restaurantapp.data.remote.dto.RegisterRequest
import com.example.restaurantapp.data.remote.dto.UserResponse

interface AuthRepository {
    suspend fun register(request: RegisterRequest): Result<Unit>
    suspend fun login(request: LoginRequest): Result<LoginResponse>
    suspend fun getMe(email: String, password: String): Result<UserResponse>
}