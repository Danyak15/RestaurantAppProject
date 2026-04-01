package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.mock.MockCategories
import com.example.restaurantapp.domain.model.Category
import com.example.restaurantapp.domain.model.Restaurant
import java.util.Locale

class CategoriesRepository {
    fun getCategoriesByRestaurantId(restaurantId: Int): List<Category> {
        return MockCategories.getCategoriesByRestaurantId(restaurantId)
    }

    fun getCategoryById(id: Int): Category? {
        return MockCategories.getCategoryById(id)
    }
}