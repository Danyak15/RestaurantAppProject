package com.example.restaurantapp.data.mock

import com.example.restaurantapp.data.mock.MockCategories.getCategories
import com.example.restaurantapp.domain.model.Category
import com.example.restaurantapp.domain.model.MenuItem

object MockMenuItems {
    fun getMenuItems(): List<MenuItem> = listOf(
        MenuItem(1, 1, "Хинкали с говядиной", 320, 150, "Сочные хинкали с бульоном"),
        MenuItem(2, 1, "Хинкали с бараниной", 350, 150, "Ароматные хинкали с пряностями"),
        MenuItem(3, 1, "Хинкали с сыром", 300, 150, "Нежные хинкали с сулугуни"),

        MenuItem(4, 2, "Хачапури по-аджарски", 380, 200, "С яйцом и сыром"),
        MenuItem(5, 2, "Хачапури по-имеретински", 280, 180, "С сыром сулугуни"),

        MenuItem(6, 6, "Филадельфия", 450, 8, "Лосось, сливочный сыр, огурец"),
        MenuItem(7, 6, "Калифорния", 420, 8, "Краб, авокадо, огурец, икра тобико"),


        MenuItem(8, 10, "Маргарита", 390, 30, "Томатный соус, моцарелла, базилик"),
        MenuItem(9, 10, "Пепперони", 450, 30, "Томатный соус, моцарелла, пепперони")
    )


    fun getMenuItemsByCategoryId(categoryId: Int): List<MenuItem> {
        return getMenuItems().filter { it.categoryId == categoryId }
    }

    fun getMenuItemById(id: Int): MenuItem? {
        return getMenuItems().find { it.id == id }
    }
}