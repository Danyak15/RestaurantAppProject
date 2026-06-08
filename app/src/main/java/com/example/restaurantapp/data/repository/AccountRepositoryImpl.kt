package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.local.auth.SessionManager
import com.example.restaurantapp.data.local.dao.UserDao
import com.example.restaurantapp.data.local.mapper.toDomain
import com.example.restaurantapp.data.local.mapper.toEntity
import com.example.restaurantapp.data.remote.api.AccountApi
import com.example.restaurantapp.data.remote.dto.request.LoginRequest
import com.example.restaurantapp.data.remote.dto.request.RegisterRequest
import com.example.restaurantapp.data.remote.dto.request.UpdateUserRequest
import com.example.restaurantapp.data.remote.dto.response.LoginResponse
import com.example.restaurantapp.data.utils.NetworkHelper
import com.example.restaurantapp.domain.model.User
import com.example.restaurantapp.domain.repository.AccountRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val accountApi: AccountApi,
    private val networkHelper: NetworkHelper,
    private val sessionManager: SessionManager
) : AccountRepository {
    override suspend fun register(
        name: String,
        surname: String,
        phone: String,
        password: String
    ): Result<Unit> {
        return try {
            networkHelper.checkInternetConnection()

            val response = accountApi.register(
                RegisterRequest(
                    name = name,
                    surname = surname,
                    phone = phone,
                    password = password
                )
            )

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Ошибка при регистрации: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(phone: String, password: String): Result<LoginResponse> {
        return try {
            networkHelper.checkInternetConnection()

            val response = accountApi.login(
                LoginRequest(
                    phone = phone,
                    password = password
                )
            )

            val body = response.body()

            if (response.isSuccessful && body != null) {
                sessionManager.saveToken(body.token)
                userDao.saveUser(body.user.toEntity())

                Result.success(body)
            } else {
                Result.failure(Exception("Ошибка при входе: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMe(): Result<User> {
       if (!sessionManager.isAuthorized()) {
            return Result.failure(Exception("Пользователь не авторизован"))
       }

        return try {
            networkHelper.checkInternetConnection()

            val response = accountApi.getMe()
            val body = response.body()

            if (response.isSuccessful && body != null) {
                userDao.saveUser(body.toEntity())
                Result.success(body.toDomain())
            } else {
                val user = userDao.getCurrentUser()

                if (user != null) {
                    Result.success(user.toDomain())
                } else {
                    Result.failure(Exception("Ошибка при получении пользователя: ${response.code()}"))
                }
            }
        } catch (e: Exception) {
            val user = userDao.getCurrentUser()

            if (user != null) {
                Result.success(user.toDomain())
            } else {
                Result.failure(e)
            }
        }
    }

    override suspend fun updateMe(
        name: String,
        surname: String,
        email: String?
    ): Result<User> {
        if (!sessionManager.isAuthorized()) {
            return Result.failure(Exception("Пользователь не авторизован"))
        }

        return try {
            networkHelper.checkInternetConnection()

            val response = accountApi.updateMe(
                request = UpdateUserRequest(
                    name = name,
                    surname = surname,
                    email = email
                )
            )

            val body = response.body()

            if (response.isSuccessful && body != null) {
                userDao.saveUser(body.toEntity())
                Result.success(body.toDomain())
            } else {
                Result.failure(Exception("Ошибка при изменении данных: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearSession() {
        sessionManager.clearSession()
        userDao.clearUser()
    }

    override fun checkAuth(): Boolean = sessionManager.isAuthorized()

    override suspend fun uploadAvatar(file: File): Result<User> {
        if (!sessionManager.isAuthorized()) {
            return Result.failure(Exception("Пользователь не авторизован"))
        }

        return try {
            networkHelper.checkInternetConnection()

            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData(
                name = "avatar",
                filename = file.name,
                body = requestBody
            )
            val response = accountApi.uploadAvatar(part)
            val body = response.body()

            if (response.isSuccessful && body != null) {
                userDao.saveUser(body.toEntity())
                Result.success(body.toDomain())
            } else {
                Result.failure(Exception("Не удалось загрузить фото: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
