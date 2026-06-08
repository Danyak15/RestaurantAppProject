package com.example.restaurantapp.data.local.mapper

import com.example.restaurantapp.data.local.entity.RestaurantEntity
import com.example.restaurantapp.data.remote.dto.response.RestaurantResponse
import com.example.restaurantapp.domain.model.Restaurant

fun RestaurantResponse.toEntity() = RestaurantEntity(
    id = id,
    name = name,
    cuisine = cuisine,
    address = address,
    description = description,
    rating = rating,
    phone = phone,
    imageUrl = imageUrl
)

fun RestaurantEntity.toDomain() = Restaurant(
    id = id,
    name = name,
    cuisine = cuisine,
    address = address,
    description = description,
    rating = rating,
    phone = phone,
    imageUrl = imageUrl
)
