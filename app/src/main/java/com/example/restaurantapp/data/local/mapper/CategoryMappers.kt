package com.example.restaurantapp.data.local.mapper

import com.example.restaurantapp.data.local.entity.CategoryEntity
import com.example.restaurantapp.domain.model.Category

fun CategoryEntity.toDomain() = Category(
    id = id,
    restaurantId = restaurantId,
    name = name
)

fun Category.toEntity() = CategoryEntity(
    id = id,
    restaurantId = restaurantId,
    name = name
)