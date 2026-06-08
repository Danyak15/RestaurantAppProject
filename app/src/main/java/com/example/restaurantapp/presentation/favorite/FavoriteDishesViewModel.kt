package com.example.restaurantapp.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.model.Restaurant
import com.example.restaurantapp.domain.usecase.favorite.FilterFavoriteDishesUseCase
import com.example.restaurantapp.domain.usecase.favorite.GetFavoriteRestaurantsUseCase
import com.example.restaurantapp.domain.usecase.favorite.ObserveFavoriteDishesUseCase
import com.example.restaurantapp.domain.usecase.favorite.SyncFavoritesUseCase
import com.example.restaurantapp.domain.usecase.restaurant.GetRestaurantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteDishesViewModel @Inject constructor(
    private val getRestaurantsUseCase: GetRestaurantsUseCase,
    private val observeFavoriteDishesUseCase: ObserveFavoriteDishesUseCase,
    private val getFavoriteRestaurantsUseCase: GetFavoriteRestaurantsUseCase,
    private val filterFavoriteDishesUseCase: FilterFavoriteDishesUseCase,
    private val syncFavoritesUseCase: SyncFavoritesUseCase
) : ViewModel() {

    init {
        loadRestaurants()
        syncFavorites()
    }

    private val selectedRestaurantId = MutableStateFlow<Long?>(null)

    private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    private val restaurants: StateFlow<List<Restaurant>> = _restaurants.asStateFlow()

    private val allFavoriteDishes = observeFavoriteDishesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val favoriteRestaurants =
        combine(restaurants, allFavoriteDishes) { restaurants, dishes ->
            getFavoriteRestaurantsUseCase(restaurants, dishes)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val favoriteDishes =
        combine(allFavoriteDishes, selectedRestaurantId) { dishes, restaurantId ->
            filterFavoriteDishesUseCase(dishes, restaurantId)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private fun syncFavorites() {
        viewModelScope.launch {
            syncFavoritesUseCase()
        }
    }

    private fun loadRestaurants() {
        viewModelScope.launch {
            _restaurants.value = getRestaurantsUseCase()
        }
    }

    fun selectRestaurant(restaurantId: Long?) {
        selectedRestaurantId.value = restaurantId
    }
}
