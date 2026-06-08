package com.example.restaurantapp.domain.repository

import com.example.restaurantapp.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    fun getCategoriesByRestaurantId(restaurantId: Long): Flow<List<Category>>
    suspend fun getCategoryById(id: Long): Category?
}