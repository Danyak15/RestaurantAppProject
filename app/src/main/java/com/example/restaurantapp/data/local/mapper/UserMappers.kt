package com.example.restaurantapp.data.local.mapper

import com.example.restaurantapp.data.local.entity.UserEntity
import com.example.restaurantapp.data.remote.dto.response.UserResponse
import com.example.restaurantapp.domain.model.User

fun UserEntity.toDomain() = User(
    id = id,
    name = name,
    surname = surname,
    phone = phone,
    email = email
)

fun UserResponse.toEntity() = UserEntity(
    id = id,
    name = name,
    surname = surname,
    phone = phone,
    email = email
)

fun UserResponse.toDomain() = User(
    id = id,
    name = name,
    surname = surname,
    phone = phone,
    email = email
)