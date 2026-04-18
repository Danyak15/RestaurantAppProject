package com.example.restaurantapp.data.remote.repository

import androidx.graphics.shapes.RoundedPolygon
import com.example.restaurantapp.data.local.auth.SessionManager
import com.example.restaurantapp.data.remote.api.AuthApi
import com.example.restaurantapp.data.remote.dto.request.LoginRequest
import com.example.restaurantapp.data.remote.dto.response.LoginResponse
import com.example.restaurantapp.data.remote.dto.request.RegisterRequest
import com.example.restaurantapp.data.remote.dto.response.UserResponse
import com.example.restaurantapp.data.remote.dto.request.UpdateUserRequest
import com.example.restaurantapp.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val sessionManager: SessionManager
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

    override suspend fun updateMe(request: UpdateUserRequest): Result<UserResponse> {
        return try {
            val email = sessionManager.getEmail() ?: ""
            val password = sessionManager.getPassword() ?: ""

            val authHeader = okhttp3.Credentials.basic(email, password)
            val response = authApi.updateMe(authHeader, request)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Update failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}