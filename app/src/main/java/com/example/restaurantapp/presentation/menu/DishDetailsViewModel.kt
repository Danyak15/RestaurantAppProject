package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.model.Dish
import com.example.restaurantapp.domain.repository.DishesRepository
import com.example.restaurantapp.domain.repository.FavoriteDishRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DishDetailsViewModel @Inject constructor(
    private val dishesRepository: DishesRepository,
    private val favoriteDishRepository: FavoriteDishRepository
) : ViewModel() {
    private val _dish = MutableStateFlow<Dish?>(null)
    val dish: StateFlow<Dish?> = _dish

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    val isFavoriteVisible: Boolean = favoriteDishRepository.isAuthorized()

    private var favoriteJob: Job? = null

    fun loadDishDetails(dishId: Int) {
        viewModelScope.launch {
            _dish.value = dishesRepository.getDishById(dishId)

            favoriteJob?.cancel()

            favoriteJob = favoriteDishRepository.observeIsFavorite(dishId)
                .onEach { favorite ->
                    _isFavorite.value = favorite
                }
                .launchIn(this)
        }
    }

    fun toggleFavorite() {
        val dishId = _dish.value?.id ?: return
        val currentlyFavorite = _isFavorite.value

        viewModelScope.launch {
            if (currentlyFavorite) {
                favoriteDishRepository.removeFromFavorites(dishId)
            } else {
                favoriteDishRepository.addToFavorites(dishId)
            }
        }
    }
}