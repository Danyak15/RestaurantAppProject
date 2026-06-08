package com.example.restaurantapp.domain.usecase.menu

import com.example.restaurantapp.domain.model.Category
import com.example.restaurantapp.domain.model.Dish
import com.example.restaurantapp.domain.repository.CategoriesRepository
import com.example.restaurantapp.domain.repository.DishesRepository
import javax.inject.Inject

data class RestaurantMenu(
    val categories: List<Category>,
    val dishes: List<Dish>
)

class GetDishesByCategoryIdUseCase @Inject constructor(
    private val dishesRepository: DishesRepository
) {
    suspend operator fun invoke(categoryId: Long) =
        dishesRepository.getDishesByCategoryId(categoryId)
}

class GetRestaurantMenuUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository,
    private val dishesRepository: DishesRepository
) {
    suspend operator fun invoke(restaurantId: Long) = RestaurantMenu(
        categories = categoriesRepository.getCategoriesByRestaurantId(restaurantId),
        dishes = dishesRepository.getDishesByRestaurantId(restaurantId)
    )
}

class SearchDishesUseCase @Inject constructor() {
    operator fun invoke(dishes: List<Dish>, searchText: String): List<Dish> {
        val trimmedText = searchText.trim()

        return if (trimmedText.isBlank()) {
            emptyList()
        } else {
            dishes.filter { dish ->
                dish.name.contains(trimmedText, ignoreCase = true)
            }
        }
    }
}

class GetDishByIdUseCase @Inject constructor(
    private val dishesRepository: DishesRepository
) {
    suspend operator fun invoke(dishId: Long) = dishesRepository.getDishById(dishId)
}
