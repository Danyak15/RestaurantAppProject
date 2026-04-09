package com.example.restaurantapp.data.local.mapper

import com.example.restaurantapp.data.local.entity.DishEntity
import com.example.restaurantapp.domain.model.Dish

fun DishEntity.toDomain() = Dish(
    id = id,
    categoryId = categoryId,
    name = name,
    price = price,
    weight = weight,
    description = description,
    isFavorite = isFavorite
)

fun Dish.toEntity() = DishEntity(
    id = id,
    categoryId = categoryId,
    name = name,
    price = price,
    weight = weight,
    description = description,
    isFavorite = isFavorite
)