package com.example.restaurantapp.domain.repository

import com.example.restaurantapp.domain.model.Dish
import kotlinx.coroutines.flow.Flow

interface DishesRepository {
    fun getDishesByCategoryId(categoryId: Long): Flow<List<Dish>>
    fun observeDishesByIds(ids: List<Long>): Flow<List<Dish>>
    fun observeDishesByRestaurantId(restaurantId: Long): Flow<List<Dish>>
    suspend fun getDishById(id: Long): Dish?
}