package com.example.restaurantapp.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.model.Dish
import com.example.restaurantapp.domain.model.Restaurant
import com.example.restaurantapp.domain.repository.DishesRepository
import com.example.restaurantapp.domain.repository.FavoriteDishRepository
import com.example.restaurantapp.domain.repository.RestaurantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteDishesViewModel @Inject constructor(
    private val restaurantsRepository: RestaurantsRepository,
    private val dishesRepository: DishesRepository,
    private val favoriteRepository: FavoriteDishRepository
) : ViewModel() {

    init {
        syncFavorites()
    }

    private val selectedRestaurantId = MutableStateFlow<Int?>(null)

    private val restaurants: StateFlow<List<Restaurant>> =
        restaurantsRepository.getRestaurants()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val allFavoriteDishes: StateFlow<List<Dish>> =
        favoriteRepository.observeFavoriteDishIds()
            .flatMapLatest { ids ->
                if (ids.isEmpty()) flowOf(emptyList())
                else dishesRepository.observeDishesByIds(ids)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val favoriteRestaurants: StateFlow<List<Restaurant>> =
        combine(
            restaurants,
            allFavoriteDishes
        ) { restaurants, dishes ->
            val restaurantIds = dishes.map { it.restaurantId }.toSet()

            restaurants
                .filter { restaurant -> restaurant.id in restaurantIds }
                .sortedBy { it.name }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val favoriteDishes: StateFlow<List<Dish>> =
        combine(
            allFavoriteDishes,
            selectedRestaurantId
        ) { dishes, restaurantId ->
            val filtered = if (restaurantId == null) {
                dishes
            } else {
                dishes.filter { it.restaurantId == restaurantId }
            }

            filtered.sortedBy { it.name }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private fun syncFavorites() {
        viewModelScope.launch {
            favoriteRepository.syncFavorites()
        }
    }

    fun selectRestaurant(restaurantId: Int?) {
        selectedRestaurantId.value = restaurantId
    }
}