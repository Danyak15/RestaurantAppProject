package com.example.restaurantapp.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.model.Dish
import com.example.restaurantapp.domain.usecase.favorite.IsFavoriteAuthorizedUseCase
import com.example.restaurantapp.domain.usecase.favorite.ObserveIsFavoriteUseCase
import com.example.restaurantapp.domain.usecase.favorite.ToggleFavoriteUseCase
import com.example.restaurantapp.domain.usecase.menu.GetDishByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DishDetailsViewModel @Inject constructor(
    private val getDishByIdUseCase: GetDishByIdUseCase,
    private val observeIsFavoriteUseCase: ObserveIsFavoriteUseCase,
    private val isFavoriteAuthorizedUseCase: IsFavoriteAuthorizedUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {
    private val _selectedDishId = MutableStateFlow<Long?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val dish: StateFlow<Dish?> = _selectedDishId
        .filterNotNull()
        .mapLatest { dishId ->
            getDishByIdUseCase(dishId)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val isFavorite: StateFlow<Boolean> = _selectedDishId
        .filterNotNull()
        .flatMapLatest { dishId ->
            observeIsFavoriteUseCase(dishId)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    val isFavoriteVisible: Boolean = isFavoriteAuthorizedUseCase()

    fun loadDishDetails(dishId: Long) {
        _selectedDishId.value = dishId
    }

    fun toggleFavorite() {
        val dishId = _selectedDishId.value ?: return

        viewModelScope.launch {
            toggleFavoriteUseCase(dishId, isFavorite.value)
        }
    }
}
