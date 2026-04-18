package com.example.restaurantapp.domain.repository

import com.example.restaurantapp.data.remote.dto.request.LoginRequest
import com.example.restaurantapp.data.remote.dto.response.LoginResponse
import com.example.restaurantapp.data.remote.dto.request.RegisterRequest
import com.example.restaurantapp.data.remote.dto.response.UserResponse
import com.example.restaurantapp.data.remote.dto.request.UpdateUserRequest

interface AuthRepository {
    suspend fun register(request: RegisterRequest): Result<Unit>
    suspend fun login(request: LoginRequest): Result<LoginResponse>
    suspend fun getMe(email: String, password: String): Result<UserResponse>
    suspend fun updateMe(request: UpdateUserRequest): Result<UserResponse>
}