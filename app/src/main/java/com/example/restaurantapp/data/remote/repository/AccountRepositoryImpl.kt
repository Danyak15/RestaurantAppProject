package com.example.restaurantapp.data.remote.repository

import com.example.restaurantapp.data.local.auth.SessionManager
import com.example.restaurantapp.data.remote.api.AccountApi
import com.example.restaurantapp.data.remote.dto.request.LoginRequest
import com.example.restaurantapp.data.remote.dto.response.LoginResponse
import com.example.restaurantapp.data.remote.dto.request.RegisterRequest
import com.example.restaurantapp.data.remote.dto.response.UserResponse
import com.example.restaurantapp.data.remote.dto.request.UpdateUserRequest
import com.example.restaurantapp.domain.repository.AccountRepository

class AccountRepositoryImpl(
    private val accountApi: AccountApi,
    private val sessionManager: SessionManager
) : AccountRepository {
    override suspend fun register(
        name: String,
        surname: String,
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            val response = accountApi.register(
                RegisterRequest(
                name = name,
                surname = surname,
                email = email,
                password = password
                )
            )

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Registration failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = accountApi.login(
                LoginRequest(
                    email = email,
                    password = password
                )
            )

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
            val response = accountApi.getMe(authHeader)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Get me failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateMe(
        name: String,
        surname: String,
        email: String
    ): Result<UserResponse> {
        return try {
            val email = sessionManager.getEmail() ?: ""
            val password = sessionManager.getPassword() ?: ""

            val authHeader = okhttp3.Credentials.basic(email, password)
            val response = accountApi.updateMe(
                authHeader = authHeader,
                request = UpdateUserRequest(
                    name = name,
                    surname = surname,
                    email = email
                )
            )

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