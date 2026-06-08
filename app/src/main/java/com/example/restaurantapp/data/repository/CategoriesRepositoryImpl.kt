package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.local.dao.CategoryDao
import com.example.restaurantapp.data.local.mapper.toDomain
import com.example.restaurantapp.domain.model.Category
import com.example.restaurantapp.domain.repository.CategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoriesRepository {
    override suspend fun getCategoriesByRestaurantId(restaurantId: Long): List<Category> {
        return categoryDao.getCategoriesByRestaurantId(restaurantId).map { it.toDomain() }
    }

    override suspend fun getCategoryById(id: Long): Category? {
        return categoryDao.getCategoryById(id)?.toDomain()
    }
}
