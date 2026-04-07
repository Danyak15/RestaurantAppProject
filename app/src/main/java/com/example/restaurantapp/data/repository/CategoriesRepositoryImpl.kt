package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.local.dao.CategoryDao
import com.example.restaurantapp.data.local.mapper.toDomain
import com.example.restaurantapp.data.local.mapper.toEntity
import com.example.restaurantapp.data.mock.MockCategories
import com.example.restaurantapp.domain.model.Category
import com.example.restaurantapp.domain.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoriesRepositoryImpl(
    private val categoryDao: CategoryDao
) : CategoriesRepository {
    override fun getCategoriesByRestaurantId(restaurantId: Int): Flow<List<Category>> {
        return categoryDao.getCategoriesByRestaurantId(restaurantId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getCategoryById(id: Int): Category? {
        return categoryDao.getCategoryById(id)?.toDomain()
    }
}