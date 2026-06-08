package com.example.restaurantapp.domain.usecase.favorite

import com.example.restaurantapp.domain.model.Dish
import com.example.restaurantapp.domain.model.Restaurant
import com.example.restaurantapp.domain.repository.DishesRepository
import com.example.restaurantapp.domain.repository.FavoriteDishRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class IsFavoriteAuthorizedUseCase @Inject constructor(
    private val favoriteDishRepository: FavoriteDishRepository
) {
    operator fun invoke() = favoriteDishRepository.isAuthorized()
}

class ObserveIsFavoriteUseCase @Inject constructor(
    private val favoriteDishRepository: FavoriteDishRepository
) {
    operator fun invoke(dishId: Long) = favoriteDishRepository.observeIsFavorite(dishId)
}

class SyncFavoritesUseCase @Inject constructor(
    private val favoriteDishRepository: FavoriteDishRepository
) {
    suspend operator fun invoke() = favoriteDishRepository.syncFavorites()
}

class ToggleFavoriteUseCase @Inject constructor(
    private val favoriteDishRepository: FavoriteDishRepository
) {
    suspend operator fun invoke(dishId: Long, currentlyFavorite: Boolean) =
        if (currentlyFavorite) {
            favoriteDishRepository.removeFromFavorites(dishId)
        } else {
            favoriteDishRepository.addToFavorites(dishId)
        }
}

class ObserveFavoriteDishesUseCase @Inject constructor(
    private val favoriteDishRepository: FavoriteDishRepository,
    private val dishesRepository: DishesRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke() = favoriteDishRepository.observeFavoriteDishIds()
        .flatMapLatest { ids ->
            if (ids.isEmpty()) {
                flowOf(emptyList())
            } else {
                dishesRepository.observeDishesByIds(ids)
            }
        }
}

class GetFavoriteRestaurantsUseCase @Inject constructor() {
    operator fun invoke(
        restaurants: List<Restaurant>,
        dishes: List<Dish>
    ): List<Restaurant> {
        val restaurantIds = dishes.map { it.restaurantId }.toSet()

        return restaurants
            .filter { restaurant -> restaurant.id in restaurantIds }
            .sortedBy { it.name }
    }
}

class FilterFavoriteDishesUseCase @Inject constructor() {
    operator fun invoke(
        dishes: List<Dish>,
        restaurantId: Long?
    ): List<Dish> {
        val filtered = if (restaurantId == null) {
            dishes
        } else {
            dishes.filter { it.restaurantId == restaurantId }
        }

        return filtered.sortedBy { it.name }
    }
}
