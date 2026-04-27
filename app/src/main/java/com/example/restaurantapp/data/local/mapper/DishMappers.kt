package com.example.restaurantapp.data.local.mapper

import com.example.restaurantapp.data.local.entity.DishEntity
import com.example.restaurantapp.domain.model.Dish

fun DishEntity.toDomain() = Dish(
    id = id,
    restaurantId = restaurantId,
    categoryId = categoryId,
    name = name,
    price = price,
    weight = weight,
    description = description
)

fun Dish.toEntity() = DishEntity(
    id = id,
    restaurantId = restaurantId,
    categoryId = categoryId,
    name = name,
    price = price,
    weight = weight,
    description = description
)