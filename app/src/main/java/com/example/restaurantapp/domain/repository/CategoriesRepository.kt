package com.example.restaurantapp.domain.repository

import com.example.restaurantapp.domain.model.Category

interface CategoriesRepository {
    suspend fun getCategoriesByRestaurantId(restaurantId: Long): List<Category>
    suspend fun getCategoryById(id: Long): Category?
}
