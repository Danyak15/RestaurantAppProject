package com.example.restaurantapp.data.local.mapper

import com.example.restaurantapp.data.local.entity.RestaurantEntity
import com.example.restaurantapp.domain.model.Restaurant

fun RestaurantEntity.toDomain() = Restaurant(
    id = id,
    name = name,
    cuisine = cuisine,
    rating = rating,
    address = address,
    description = description
)

fun Restaurant.toEntity() = RestaurantEntity(
    id = id,
    name = name,
    cuisine = cuisine,
    rating = rating,
    address = address,
    description = description
)