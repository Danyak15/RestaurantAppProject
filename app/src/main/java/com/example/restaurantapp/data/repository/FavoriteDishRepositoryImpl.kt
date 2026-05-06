package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.local.auth.SessionManager
import com.example.restaurantapp.data.local.dao.FavoriteDishDao
import com.example.restaurantapp.data.local.dao.FavoriteSyncDao
import com.example.restaurantapp.data.local.dao.UserDao
import com.example.restaurantapp.data.local.entity.FavoriteDishEntity
import com.example.restaurantapp.data.local.entity.FavoriteSyncEntity
import com.example.restaurantapp.data.remote.api.FavoriteApi
import com.example.restaurantapp.data.utils.NetworkHelper
import com.example.restaurantapp.data.worker.FavoriteSyncScheduler
import com.example.restaurantapp.domain.repository.FavoriteDishRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FavoriteDishRepositoryImpl @Inject constructor(
    private val favoriteDishDao: FavoriteDishDao,
    private val favoriteSyncDao: FavoriteSyncDao,
    private val favoriteApi: FavoriteApi,
    private val userDao: UserDao,
    private val sessionManager: SessionManager,
    private val networkHelper: NetworkHelper,
    private val favoriteSyncScheduler: FavoriteSyncScheduler
) : FavoriteDishRepository {
    override fun isAuthorized(): Boolean = sessionManager.isAuthorized()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeIsFavorite(dishId: Int): Flow<Boolean> {
        if (!sessionManager.isAuthorized()) {
            return flowOf(false)
        }
        return userDao.observeCurrentUser().flatMapLatest { user ->
            if (user == null) {
                flowOf(false)
            } else {
                favoriteDishDao.observeIsFavorite(user.id, dishId)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeFavoriteDishIds(): Flow<List<Int>> {
        if (!sessionManager.isAuthorized()) {
            return flowOf(emptyList())
        }

        return userDao.observeCurrentUser().flatMapLatest { user ->
            if (user == null) {
                flowOf(emptyList())
            } else {
                favoriteDishDao.observeFavoriteDishIds(user.id)
            }
        }
    }

    override suspend fun addToFavorites(dishId: Int): Result<Unit> {
        return try {
            val userId = getCurrentUserId()

            favoriteDishDao.addToFavorites(
                FavoriteDishEntity(
                    userId = userId,
                    dishId = dishId
                )
            )

            val existingTask = favoriteSyncDao.getTask(userId, dishId)

            if (existingTask?.action == "REMOVE") {
                favoriteSyncDao.removeTask(userId, dishId)
            } else {
                favoriteSyncDao.addTask(
                    FavoriteSyncEntity(
                        userId = userId,
                        dishId = dishId,
                        action = "ADD"
                    )
                )
            }

            trySyncItem(userId, dishId, "ADD")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeFromFavorites(dishId: Int): Result<Unit> {
        return try {
            val userId = getCurrentUserId()

            favoriteDishDao.removeFromFavorites(userId, dishId)

            val existingTask = favoriteSyncDao.getTask(userId, dishId)

            if (existingTask?.action == "ADD") {
                favoriteSyncDao.removeTask(userId, dishId)
            } else {
                favoriteSyncDao.addTask(
                    FavoriteSyncEntity(
                        userId = userId,
                        dishId = dishId,
                        action = "REMOVE"
                    )
                )
            }

            trySyncItem(userId, dishId, "REMOVE")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun trySyncItem(userId: Long, dishId: Int, action: String): Result<Unit> {
        return try {
            networkHelper.checkInternetConnection()

            val response = when (action) {
                "ADD" -> favoriteApi.addToFavorites(dishId)
                "REMOVE" -> favoriteApi.removeFromFavorites(dishId)
                else -> return Result.failure(Exception("Неизвестная команда"))
            }

            if (response.isSuccessful) {
                favoriteSyncDao.removeTask(userId, dishId)
            } else {
                favoriteSyncScheduler.schedule()
            }

            Result.success(Unit)
        } catch (_: Exception) {
            favoriteSyncScheduler.schedule()
            Result.success(Unit)
        }
    }

    override suspend fun pushFavorites(): Result<Unit> {
        return try {
            networkHelper.checkInternetConnection()

            val userId = getCurrentUserId()

            val tasks = favoriteSyncDao.getTasks(userId)
            var allSuccessful = true

            tasks.forEach { task ->
                val response = when (task.action) {
                    "ADD" -> favoriteApi.addToFavorites(task.dishId)
                    "REMOVE" -> favoriteApi.removeFromFavorites(task.dishId)
                    else -> null
                }

                if (response?.isSuccessful == true) {
                    favoriteSyncDao.removeTask(userId, task.dishId)
                } else {
                    allSuccessful = false
                }
            }

            if (allSuccessful) {
                Result.success(Unit)
            } else {
                favoriteSyncScheduler.schedule()
                Result.failure(Exception("Не все изменения были обработаны"))
            }
        } catch (e: Exception) {
            favoriteSyncScheduler.schedule()
            Result.failure(e)
        }
    }

    override suspend fun pullFavorites(): Result<Unit> {
        return try {
            networkHelper.checkInternetConnection()

            val userId = getCurrentUserId()

            val response = favoriteApi.getFavoriteDishes()

            if (response.isSuccessful && response.body() != null)  {
                val favoriteIds = response.body()!!
                favoriteDishDao.updateFavoriteDishes(userId, favoriteIds)

                Result.success(Unit)
            } else {
                Result.failure(Exception("Ошибка при загрузке избранных блюд: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun syncFavorites(): Result<Unit> {
        val pushResult = pushFavorites()

        return if (pushResult.isFailure) {
            pushResult
        } else {
            pullFavorites()
        }
    }

    override suspend fun clearFavorites(): Result<Unit> {
        return try {
            val userId = getCurrentUserId()

            favoriteSyncDao.clearTasks(userId)
            favoriteDishDao.clearFavorites(userId)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun getCurrentUserId(): Long {
        if (!sessionManager.isAuthorized()) {
            throw Exception("Пользователь не авторизован")
        }

        return userDao.getCurrentUser()?.id
            ?: throw Exception("Пользователь не найден в локальной базе")
    }
}