package com.example.restaurantapp.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.model.News
import com.example.restaurantapp.domain.model.Restaurant
import com.example.restaurantapp.domain.usecase.news.GetNewsUseCase
import com.example.restaurantapp.domain.usecase.restaurant.GetRestaurantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getRestaurantsUseCase: GetRestaurantsUseCase,
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {
    private val selectedRestaurantId = MutableStateFlow<Long?>(null)

    private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants.asStateFlow()

    private val _news = MutableStateFlow<List<News>>(emptyList())
    val news: StateFlow<List<News>> = _news.asStateFlow()

    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> = _message.asSharedFlow()

    init {
        loadRestaurants()
        observeSelectedRestaurant()
    }

    fun selectRestaurant(restaurantId: Long?) {
        selectedRestaurantId.value = restaurantId
    }

    fun observeSelectedRestaurant() {
        viewModelScope.launch {
            selectedRestaurantId.collectLatest { restaurantId ->
                loadNews(restaurantId)
            }
        }
    }

    private fun loadRestaurants() {
        viewModelScope.launch {
            _restaurants.value = getRestaurantsUseCase()
        }
    }

    private suspend fun loadNews(restaurantId: Long?) {
        getNewsUseCase(restaurantId)
            .onSuccess { news ->
                _news.value = news
            }.onFailure { error ->
                _message.emit(error.message ?: "Не удалось загрузить новости")
            }
    }
}
