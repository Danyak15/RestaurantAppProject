package com.example.restaurantapp.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.restaurantapp.RestaurantApplication

class FavoriteSyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val appContainer =
            (applicationContext as RestaurantApplication).appContainer
        return try {
            val result = appContainer.favoriteDishRepository.pushFavorites()

            if (result.isSuccess) Result.success() else Result.retry()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}