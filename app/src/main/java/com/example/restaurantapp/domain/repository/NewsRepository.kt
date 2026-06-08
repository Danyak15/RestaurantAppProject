package com.example.restaurantapp.domain.repository

import com.example.restaurantapp.domain.model.News

interface NewsRepository {
    suspend fun getNews(restaurantId: Long? = null): Result<List<News>>
}