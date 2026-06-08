package com.example.restaurantapp.presentation.restaurants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.model.Restaurant
import com.example.restaurantapp.domain.usecase.restaurant.GetRestaurantsUseCase
import com.example.restaurantapp.domain.usecase.sync.SyncUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    private val getRestaurantsUseCase: GetRestaurantsUseCase,
    private val syncUseCase: SyncUseCase
) : ViewModel() {
    private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants.asStateFlow()

    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> = _message.asSharedFlow()

    init {
        sync()
    }

    fun sync() {
        viewModelScope.launch {
            refreshRestaurants()
            _isSyncing.value = true
            try {
                syncUseCase().onFailure { error ->
                    _message.emit(error.message ?: "Ошибка синхронизации")
                }
                refreshRestaurants()
            } finally {
                _isSyncing.value = false
            }
        }
    }

    private suspend fun refreshRestaurants() {
        _restaurants.value = getRestaurantsUseCase()
    }
}
