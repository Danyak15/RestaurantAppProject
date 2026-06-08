package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.local.dao.DishDao
import com.example.restaurantapp.data.local.mapper.toDomain
import com.example.restaurantapp.domain.model.Dish
import com.example.restaurantapp.domain.repository.DishesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DishesRepositoryImpl @Inject constructor(
    private val dishDao: DishDao
) : DishesRepository {
    override suspend fun getDishesByCategoryId(categoryId: Long): List<Dish> {
        return dishDao.getDishesByCategoryId(categoryId).map { it.toDomain() }
    }

    override suspend fun getDishesByRestaurantId(restaurantId: Long): List<Dish> {
        return dishDao.getDishesByRestaurantId(restaurantId).map { it.toDomain() }
    }

    override fun observeDishesByIds(ids: List<Long>): Flow<List<Dish>> {
        return dishDao.observeDishesByIds(ids).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getDishById(id: Long): Dish? {
        return dishDao.getDishById(id)?.toDomain()
    }
}
