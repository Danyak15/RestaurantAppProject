package com.example.restaurantapp.data.mock

import com.example.restaurantapp.domain.model.MenuItem

object MockMenuItems {
    fun getMenuItemsByCategoryId(categoryId: Int): List<MenuItem> {
        return when (categoryId) {
            1 -> listOf(
                MenuItem(1, 1, "Хинкали с говядиной", 320, 150, "Сочные хинкали с бульоном"),
                MenuItem(2, 1, "Хинкали с бараниной", 350, 150, "Ароматные хинкали с пряностями"),
                MenuItem(3, 1, "Хинкали с сыром", 300, 150, "Нежные хинкали с сулугуни")
            )
            2 -> listOf(
                MenuItem(4, 2, "Хачапури по-аджарски", 380, 200, "С яйцом и сыром"),
                MenuItem(5, 2, "Хачапури по-имеретински", 280, 180, "С сыром сулугуни")
            )
            6 -> listOf(
                MenuItem(6, 6, "Филадельфия", 450, 8, "Лосось, сливочный сыр, огурец"),
                MenuItem(7, 6, "Калифорния", 420, 8, "Краб, авокадо, огурец, икра тобико")
            )
            10 -> listOf(
                MenuItem(8, 10, "Маргарита", 390, 30, "Томатный соус, моцарелла, базилик"),
                MenuItem(9, 10, "Пепперони", 450, 30, "Томатный соус, моцарелла, пепперони")
            )
            else -> emptyList()
        }
    }
}