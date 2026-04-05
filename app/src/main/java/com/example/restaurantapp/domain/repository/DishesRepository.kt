package com.example.restaurantapp.domain.repository

import com.example.restaurantapp.domain.model.Dish

interface DishesRepository {
    fun getDishesByCategoryId(categoryId: Int): List<Dish>
    fun getDishById(id: Int): Dish?
}