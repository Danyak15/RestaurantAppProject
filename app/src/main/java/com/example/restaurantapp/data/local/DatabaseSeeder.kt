package com.example.restaurantapp.data.local

import com.example.restaurantapp.data.local.dao.CategoryDao
import com.example.restaurantapp.data.local.dao.DishDao
import com.example.restaurantapp.data.local.dao.RestaurantDao
import com.example.restaurantapp.data.local.mapper.toEntity
import com.example.restaurantapp.data.mock.MockCategories
import com.example.restaurantapp.data.mock.MockDishes
import com.example.restaurantapp.data.mock.MockRestaurants
import javax.inject.Inject

class DatabaseSeeder @Inject constructor(
    private val restaurantDao: RestaurantDao,
    private val categoryDao: CategoryDao,
    private val dishDao: DishDao
) {

    suspend fun seedIfNeeded() {
        if (restaurantDao.getCount() > 0) return

        restaurantDao.insertAll(
            MockRestaurants.getRestaurants().map { it.toEntity() }
        )

        categoryDao.insertAll(
            MockCategories.getCategories().map { it.toEntity() }
        )

        dishDao.insertAll(
            MockDishes.getDishes().map { it.toEntity() }
        )
    }
}