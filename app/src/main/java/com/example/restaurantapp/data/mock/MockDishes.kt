package com.example.restaurantapp.data.mock

import com.example.restaurantapp.domain.model.Dish

object MockDishes {
    fun getDishes(): List<Dish> = listOf(
        Dish(1, 1, 1, "Хинкали с говядиной", 320, 150, "Сочные хинкали с бульоном"),
        Dish(2, 1, 1, "Хинкали с бараниной", 350, 150, "Ароматные хинкали с пряностями"),
        Dish(3, 1, 1, "Хинкали с сыром", 300, 150, "Нежные хинкали с сулугуни"),

        Dish(4, 1, 2, "Хачапури по-аджарски", 380, 200, "С яйцом и сыром"),
        Dish(5, 1, 2, "Хачапури по-имеретински", 280, 180, "С сыром сулугуни"),

        Dish(6, 2, 6, "Филадельфия", 450, 8, "Лосось, сливочный сыр, огурец"),
        Dish(7, 2, 6, "Калифорния", 420, 8, "Краб, авокадо, огурец, икра тобико"),


        Dish(8, 3, 10, "Маргарита", 390, 30, "Томатный соус, моцарелла, базилик"),
        Dish(9, 3, 10, "Пепперони", 450, 30, "Томатный соус, моцарелла, пепперони")
    )


    fun getDishesByCategoryId(categoryId: Int): List<Dish> {
        return getDishes().filter { it.categoryId == categoryId }
    }

    fun getDishById(id: Int): Dish? {
        return getDishes().find { it.id == id }
    }
}