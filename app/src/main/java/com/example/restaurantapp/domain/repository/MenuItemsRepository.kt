package com.example.restaurantapp.domain.repository

import com.example.restaurantapp.domain.model.MenuItem

interface MenuItemsRepository {
    fun getMenuItemsByCategoryId(categoryId: Int): List<MenuItem>
    fun getMenuItemById(id: Int): MenuItem?
}