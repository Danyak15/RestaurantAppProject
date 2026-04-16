package com.example.restaurantapp.data.remote.repository

import com.example.restaurantapp.data.remote.api.AuthApi
import com.example.restaurantapp.data.remote.dto.LoginRequest
import com.example.restaurantapp.data.remote.dto.LoginResponse
import com.example.restaurantapp.data.remote.dto.RegisterRequest
import com.example.restaurantapp.data.remote.dto.UserResponse
import com.example.restaurantapp.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun register(request: RegisterRequest): Result<Unit> {
        return try {
            val response = authApi.register(request)

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Registration failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = authApi.login(request)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMe(email: String, password: String): Result<UserResponse> {
        return try {
            val authHeader = okhttp3.Credentials.basic(email, password)
            val response = authApi.getMe(authHeader)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Get me failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}