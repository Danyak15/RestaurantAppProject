package com.example.restaurantapp.data.local.mapper

import com.example.restaurantapp.data.local.entity.UserEntity
import com.example.restaurantapp.data.remote.dto.response.UserResponse

fun UserEntity.toResponse() = UserResponse(
    id = id,
    name = name,
    surname = surname,
    email = email
)

fun UserResponse.toEntity() = UserEntity(
    id = id,
    email = email,
    name = name,
    surname = surname
)