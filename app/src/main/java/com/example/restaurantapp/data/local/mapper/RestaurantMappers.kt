package com.example.restaurantapp.data.local.mapper

import com.example.restaurantapp.data.local.entity.RestaurantHoursEntity
import com.example.restaurantapp.data.local.entity.RestaurantEntity
import com.example.restaurantapp.data.local.entity.RestaurantWithHours
import com.example.restaurantapp.data.remote.dto.response.RestaurantHoursResponse
import com.example.restaurantapp.data.remote.dto.response.RestaurantResponse
import com.example.restaurantapp.domain.model.Restaurant
import com.example.restaurantapp.domain.model.RestaurantWorkingHours
import java.time.DayOfWeek
import java.time.LocalTime

fun RestaurantResponse.toEntity() = RestaurantEntity(
    id = id,
    name = name,
    cuisine = cuisine,
    address = address,
    description = description,
    rating = rating,
    phone = phone,
    imageUrl = imageUrl,
    minGuests = minGuests,
    maxGuests = maxGuests
)

fun RestaurantResponse.toHourEntities() = workingHours.map { hours ->
    hours.toEntity(restaurantId = id)
}

fun RestaurantHoursResponse.toEntity(restaurantId: Long) = RestaurantHoursEntity(
    restaurantId = restaurantId,
    dayOfWeek = dayOfWeek,
    openTime = openTime,
    closeTime = closeTime,
    isClosed = isClosed
)

fun RestaurantEntity.toDomain() = Restaurant(
    id = id,
    name = name,
    cuisine = cuisine,
    address = address,
    description = description,
    rating = rating,
    phone = phone,
    imageUrl = imageUrl,
    workingHours = emptyList(),
    minGuests = minGuests,
    maxGuests = maxGuests
)

fun RestaurantWithHours.toDomain() = restaurant.toDomain().copy(
    workingHours = workingHours
        .map { it.toDomain() }
        .sortedBy { it.dayOfWeek.value }
)

private fun RestaurantHoursEntity.toDomain() = RestaurantWorkingHours(
    dayOfWeek = DayOfWeek.valueOf(dayOfWeek),
    openTime = openTime?.let { LocalTime.parse(it) },
    closeTime = closeTime?.let { LocalTime.parse(it) },
    isClosed = isClosed
)
