package com.example.restaurantapp.domain.repository

import com.example.restaurantapp.domain.model.Category

interface CategoriesRepository {
    fun getCategoriesByRestaurantId(restaurantId: Int): List<Category>
    fun getCategoryById(id: Int): Category?
}