package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.local.dao.DishDao
import com.example.restaurantapp.data.local.mapper.toDomain
import com.example.restaurantapp.data.mock.MockDishes
import com.example.restaurantapp.domain.model.Dish
import com.example.restaurantapp.domain.repository.DishesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DishesRepositoryImpl(
    private val dishDao: DishDao
) : DishesRepository {
    override fun getDishesByCategoryId(categoryId: Int): Flow<List<Dish>> {
        return dishDao.getDishesByCategoryId(categoryId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getDishById(id: Int): Dish? {
        return dishDao.getDishById(id)?.toDomain()
    }
}