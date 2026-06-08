package com.example.restaurantapp.data.local.mapper

import com.example.restaurantapp.data.local.entity.CategoryEntity
import com.example.restaurantapp.data.remote.dto.response.CategoryResponse
import com.example.restaurantapp.domain.model.Category

fun CategoryResponse.toEntity() = CategoryEntity(
    id = id,
    restaurantId = restaurantId,
    name = name,
    displayOrder = displayOrder
)

fun CategoryEntity.toDomain() = Category(
    id = id,
    restaurantId = restaurantId,
    name = name,
    displayOrder = displayOrder
)
