package com.example.restaurantapp.data.local.mapper

import com.example.restaurantapp.data.remote.dto.response.NewsResponse
import com.example.restaurantapp.domain.model.News

fun NewsResponse.toDomain() = News(
    id = id,
    restaurantId = restaurantId,
    title = title,
    content = content,
    createdAt = createdAt,
    imageUrl = imageUrl
)
