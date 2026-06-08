package com.example.restaurantapp.domain.repository

import com.example.restaurantapp.domain.model.Dish
import kotlinx.coroutines.flow.Flow

interface DishesRepository {
    suspend fun getDishesByCategoryId(categoryId: Long): List<Dish>
    suspend fun getDishesByRestaurantId(restaurantId: Long): List<Dish>
    fun observeDishesByIds(ids: List<Long>): Flow<List<Dish>>
    suspend fun getDishById(id: Long): Dish?
}
