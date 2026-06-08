package com.example.restaurantapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.restaurantapp.data.local.entity.RestaurantHoursEntity
import com.example.restaurantapp.data.local.entity.RestaurantEntity
import com.example.restaurantapp.data.local.entity.RestaurantWithHours
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {
    @Transaction
    @Query("SELECT * FROM restaurants")
    fun getRestaurantsWithHours(): Flow<List<RestaurantWithHours>>

    @Transaction
    @Query("SELECT * FROM restaurants WHERE id = :id")
    suspend fun getRestaurantWithHoursById(id: Long): RestaurantWithHours?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurants(items: List<RestaurantEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHours(items: List<RestaurantHoursEntity>)

    @Transaction
    @Query("DELETE FROM restaurant_hours WHERE restaurantId IN (:restaurantIds)")
    suspend fun deleteHoursByRestaurantIds(restaurantIds: List<Long>)

    @Transaction
    suspend fun insertRestaurantsWithHours(
        restaurants: List<RestaurantEntity>,
        hours: List<RestaurantHoursEntity>
    ) {
        insertRestaurants(restaurants)

        val restaurantIds = restaurants.map { it.id }
        if (restaurantIds.isNotEmpty()) {
            deleteHoursByRestaurantIds(restaurantIds)
        }

        if (hours.isNotEmpty()) {
            insertHours(hours)
        }
    }
}
