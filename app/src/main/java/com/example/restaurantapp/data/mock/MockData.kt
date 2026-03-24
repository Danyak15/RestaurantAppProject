package com.example.restaurantapp.data.mock

import com.example.restaurantapp.data.models.Restaurant

object MockData {

    fun getRestaurants(): List<Restaurant> = listOf(
        Restaurant(
            id = 1,
            name = "Угли-Угли",
            cuisine = "Грузинская",
            rating = 4.8,
            imageUrl = "",
            address = "ул. Тверская, 15",
            description = "Аутентичная грузинская кухня. Хачапури по-аджарски, хинкали с разными начинками, домашнее вино. Уютный дворик с летней верандой."
        ),
        Restaurant(
            id = 2,
            name = "Сахалин",
            cuisine = "Японская",
            rating = 4.6,
            imageUrl = "",
            address = "ул. Арбат, 10",
            description = "Свежайшие роллы, сашими и авторские сеты. Рыба привозится ежедневно из Мурманска и Владивостока. Лучшее место для любителей японской кухни."
        ),
        Restaurant(
            id = 3,
            name = "Mamma Mia",
            cuisine = "Итальянская",
            rating = 4.9,
            imageUrl = "",
            address = "ул. Покровка, 28",
            description = "Домашняя паста, пицца из дровяной печи, ризотто с трюфелем. Атмосфера настоящей итальянской траттории. Каждую пятницу — живая музыка."
        ),
        Restaurant(
            id = 4,
            name = "Валенсия",
            cuisine = "Испанская",
            rating = 4.7,
            imageUrl = "",
            address = "ул. Мясницкая, 22",
            description = "Паэлья на открытом огне, тапас с хамоном, лучшая коллекция испанских вин. Авторская кухня от шеф-повара из Барселоны."
        ),
        Restaurant(
            id = 5,
            name = "Тайская история",
            cuisine = "Тайская",
            rating = 4.5,
            imageUrl = "",
            address = "ул. Пятницкая, 34",
            description = "Острые супы Том-Ям, Пад-Тай с креветками, зеленый карри. Интерьер в стиле тайского храма. Для любителей азиатской экзотики."
        ),
        Restaurant(
            id = 6,
            name = "Стейк-хаус №1",
            cuisine = "Американская",
            rating = 4.8,
            imageUrl = "",
            address = "ул. Новый Арбат, 21",
            description = "Мраморная говядина австралийского откорма, стейки на углях, картофель фри с трюфельным маслом. Собственная камера сухого вызревания мяса."
        ),
        Restaurant(
            id = 7,
            name = "Лебединое озеро",
            cuisine = "Русская",
            rating = 4.7,
            imageUrl = "",
            address = "наб. Тараса Шевченко, 5",
            description = "Борщ с пампушками, пельмени с олениной, налим по-монастырски. Вид на Москву-реку. Традиционная русская кухня в современном прочтении."
        ),
        Restaurant(
            id = 8,
            name = "Либань",
            cuisine = "Ливанская",
            rating = 4.6,
            imageUrl = "",
            address = "ул. Большая Дмитровка, 11",
            description = "Хумус, фалафель, шаурма на вертеле, запеченные овощи. Атмосфера восточного базара. Вегетарианские и веганские опции."
        ),
        Restaurant(
            id = 9,
            name = "Biergarten",
            cuisine = "Немецкая",
            rating = 4.4,
            imageUrl = "",
            address = "Кутузовский проспект, 12",
            description = "Свиная рулька, колбаски на гриле, крафтовое пиво собственной варки. Большой пивной сад. Трансляции футбола и хоккея."
        ),
        Restaurant(
            id = 10,
            name = "Морской бриз",
            cuisine = "Средиземноморская",
            rating = 4.9,
            imageUrl = "",
            address = "ул. Остоженка, 7",
            description = "Свежие устрицы, дорадо на гриле, ризотто с морепродуктами. Белоснежный интерьер в морском стиле. Идеально для романтического ужина."
        )
    )

    fun getRestaurantById(id: Int): Restaurant? {
        return getRestaurants().find { it.id == id }
    }
}