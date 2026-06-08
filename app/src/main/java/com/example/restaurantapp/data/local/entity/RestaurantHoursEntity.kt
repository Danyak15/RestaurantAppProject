package com.example.restaurantapp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "restaurant_hours",
    primaryKeys = ["restaurantId", "dayOfWeek"],
    foreignKeys = [
        ForeignKey(
            entity = RestaurantEntity::class,
            parentColumns = ["id"],
            childColumns = ["restaurantId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("restaurantId")]
)
data class RestaurantHoursEntity(
    val restaurantId: Long,
    val dayOfWeek: String,
    val openTime: String?,
    val closeTime: String?,
    val isClosed: Boolean
)
