package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.mock.MockMenuItems
import com.example.restaurantapp.domain.model.MenuItem
import com.example.restaurantapp.domain.repository.MenuItemsRepository

class MenuItemsRepositoryImpl : MenuItemsRepository {
    override fun getMenuItemsByCategoryId(categoryId: Int): List<MenuItem> {
        return MockMenuItems.getMenuItemsByCategoryId(categoryId)
    }

    override fun getMenuItemById(id: Int): MenuItem? {
        return MockMenuItems.getMenuItemById(id)
    }
}