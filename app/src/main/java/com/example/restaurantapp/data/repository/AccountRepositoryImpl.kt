package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.local.auth.SessionManager
import com.example.restaurantapp.data.local.dao.UserDao
import com.example.restaurantapp.data.local.mapper.toEntity
import com.example.restaurantapp.data.local.mapper.toResponse
import com.example.restaurantapp.data.remote.api.AccountApi
import com.example.restaurantapp.data.remote.dto.request.LoginRequest
import com.example.restaurantapp.data.remote.dto.request.RegisterRequest
import com.example.restaurantapp.data.remote.dto.request.UpdateUserRequest
import com.example.restaurantapp.data.remote.dto.response.LoginResponse
import com.example.restaurantapp.data.remote.dto.response.UserResponse
import com.example.restaurantapp.data.utils.NetworkHelper
import com.example.restaurantapp.domain.repository.AccountRepository
import okhttp3.Credentials

class AccountRepositoryImpl(
    private val userDao: UserDao,
    private val accountApi: AccountApi,
    private val networkHelper: NetworkHelper,
    private val sessionManager: SessionManager
) : AccountRepository {
    override suspend fun register(
        name: String,
        surname: String,
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            networkHelper.checkInternetConnection()

            val response = accountApi.register(
                RegisterRequest(
                    name = name,
                    surname = surname,
                    email = email,
                    password = password
                )
            )

            if (response.isSuccessful) {
                sessionManager.saveCredentials(email, password)
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
            networkHelper.checkInternetConnection()

            val response = accountApi.login(
                LoginRequest(
                    email = email,
                    password = password
                )
            )

            if (response.isSuccessful && response.body() != null) {
                sessionManager.saveCredentials(email, password)
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMe(): Result<UserResponse> {
        val email = sessionManager.getEmail()
            ?: return Result.failure(Exception("Пользователь не авторизован"))
        val password = sessionManager.getPassword()
            ?: return Result.failure(Exception("Пользователь не авторизован"))

        return try {
            networkHelper.checkInternetConnection()

            val authHeader = Credentials.basic(email, password)
            val response = accountApi.getMe(authHeader)

            if (response.isSuccessful && response.body() != null) {
                userDao.saveUser(response.body()!!.toEntity())
                Result.success(response.body()!!)
            } else {
                val user = userDao.getUser(email)

                if (user != null) {
                    Result.success(user.toResponse())
                } else {
                    Result.failure(Exception("Get me failed: ${response.code()}"))
                }
            }
        } catch (e: Exception) {
            val user = userDao.getUser(email)

            if (user != null) {
                Result.success(user.toResponse())
            } else {
                Result.failure(e)
            }
        }
    }

    override suspend fun updateMe(
        name: String,
        surname: String,
        email: String
    ): Result<UserResponse> {
        val currentEmail = sessionManager.getEmail()
            ?: return Result.failure(Exception("Пользователь не авторизован"))
        val password = sessionManager.getPassword()
            ?: return Result.failure(Exception("Пользователь не авторизован"))

        return try {
            networkHelper.checkInternetConnection()

            val authHeader = Credentials.basic(currentEmail, password)
            val response = accountApi.updateMe(
                authHeader = authHeader,
                request = UpdateUserRequest(
                    name = name,
                    surname = surname,
                    email = email
                )
            )

            if (response.isSuccessful && response.body() != null) {
                sessionManager.saveCredentials(email, password)
                userDao.saveUser(response.body()!!.toEntity())
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Update failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearSession() {
        val email = sessionManager.getEmail() ?: return
        sessionManager.clearSession()
        userDao.deleteUser(email)
    }

    override fun checkAuth(): Boolean = sessionManager.isAuthorized()
}