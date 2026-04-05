package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.mock.MockCategories
import com.example.restaurantapp.domain.model.Category
import com.example.restaurantapp.domain.repository.CategoriesRepository

class CategoriesRepositoryImpl : CategoriesRepository {
    override fun getCategoriesByRestaurantId(restaurantId: Int): List<Category> {
        return MockCategories.getCategoriesByRestaurantId(restaurantId)
    }

    override fun getCategoryById(id: Int): Category? {
        return MockCategories.getCategoryById(id)
    }
}