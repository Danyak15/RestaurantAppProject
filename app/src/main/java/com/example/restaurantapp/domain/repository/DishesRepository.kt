package com.example.restaurantapp.domain.repository

import com.example.restaurantapp.domain.model.Dish
import kotlinx.coroutines.flow.Flow

interface DishesRepository {
    fun getDishesByCategoryId(categoryId: Int): Flow<List<Dish>>
    suspend fun getDishById(id: Int): Dish?
}