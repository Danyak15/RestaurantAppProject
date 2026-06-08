package com.example.restaurantapp.data.local.mapper

import com.example.restaurantapp.data.local.entity.DishEntity
import com.example.restaurantapp.data.remote.dto.response.DishResponse
import com.example.restaurantapp.domain.model.Dish

fun DishResponse.toEntity() = DishEntity(
    id = id,
    restaurantId = restaurantId,
    categoryId = categoryId,
    name = name,
    price = price,
    weight = weight,
    description = description,
    displayOrder = displayOrder,
    imageUrl = imageUrl
)

fun DishEntity.toDomain() = Dish(
    id = id,
    restaurantId = restaurantId,
    categoryId = categoryId,
    name = name,
    price = price,
    weight = weight,
    description = description,
    displayOrder = displayOrder,
    imageUrl = imageUrl
)
