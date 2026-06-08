package com.example.restaurantapp.data.remote.dto.response

data class SyncResponse(
    val restaurants: List<RestaurantResponse>,
    val categories: List<CategoryResponse>,
    val dishes: List<DishResponse>
)
