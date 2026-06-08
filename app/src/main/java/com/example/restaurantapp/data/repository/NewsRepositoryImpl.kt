package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.local.mapper.toDomain
import com.example.restaurantapp.data.remote.api.NewsApi
import com.example.restaurantapp.data.utils.NetworkHelper
import com.example.restaurantapp.domain.model.News
import com.example.restaurantapp.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val networkHelper: NetworkHelper
) : NewsRepository {
    override suspend fun getNews(restaurantId: Long?): Result<List<News>> {
        return try {
            networkHelper.checkInternetConnection()

            val response = newsApi.getNews(restaurantId)
            val body = response.body()

            if (response.isSuccessful && body != null) {
                Result.success(body.map { it.toDomain() })
            } else {
                Result.failure(Exception("Ошибка загрузки новостей: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}