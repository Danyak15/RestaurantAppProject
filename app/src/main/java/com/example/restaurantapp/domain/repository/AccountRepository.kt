package com.example.restaurantapp.domain.repository

import com.example.restaurantapp.data.remote.dto.response.LoginResponse
import com.example.restaurantapp.data.remote.dto.response.UserResponse

interface AccountRepository {
    suspend fun register(
        name: String,
        surname: String,
        email: String,
        password: String
    ): Result<Unit>

    suspend fun login(email: String, password: String): Result<LoginResponse>

    suspend fun getMe(email: String, password: String): Result<UserResponse>

    suspend fun updateMe(
        name: String,
        surname: String,
        email: String
    ): Result<UserResponse>
}