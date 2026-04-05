package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.mock.MockDishes
import com.example.restaurantapp.domain.model.Dish
import com.example.restaurantapp.domain.repository.DishesRepository

class DishesRepositoryImpl : DishesRepository {
    override fun getDishesByCategoryId(categoryId: Int): List<Dish> {
        return MockDishes.getDishesByCategoryId(categoryId)
    }

    override fun getDishById(id: Int): Dish? {
        return MockDishes.getDishById(id)
    }
}