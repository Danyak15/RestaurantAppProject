package com.example.restaurantapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RestaurantWithHours(
    @Embedded val restaurant: RestaurantEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "restaurantId"
    )
    val workingHours: List<RestaurantHoursEntity>
)
