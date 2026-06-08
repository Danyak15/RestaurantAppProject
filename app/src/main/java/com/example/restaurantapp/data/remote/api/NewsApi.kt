package com.example.restaurantapp.data.remote.api

import com.example.restaurantapp.data.remote.dto.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("/api/news")
    suspend fun getNews(
        @Query("restaurantId") restaurantId: Long? = null
    ): Response<List<NewsResponse>>
}