package com.example.restaurantapp.domain.repository

import com.example.restaurantapp.data.remote.dto.response.LoginResponse
import com.example.restaurantapp.domain.model.User

interface AccountRepository {
    suspend fun register(
        name: String,
        surname: String,
        phone: String,
        password: String
    ): Result<Unit>

    suspend fun login(phone: String, password: String): Result<LoginResponse>

    suspend fun getMe(): Result<User>

    suspend fun updateMe(
        name: String,
        surname: String,
        email: String?
    ): Result<User>

    suspend fun clearSession()
    fun checkAuth(): Boolean
}