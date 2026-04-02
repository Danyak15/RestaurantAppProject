package com.example.restaurantapp.data.repository

import com.example.restaurantapp.data.mock.MockMenuItems
import com.example.restaurantapp.domain.model.MenuItem

class MenuItemsRepository {
    fun getMenuItemsByCategoryId(categoryId: Int): List<MenuItem> {
        return MockMenuItems.getMenuItemsByCategoryId(categoryId)
    }
}