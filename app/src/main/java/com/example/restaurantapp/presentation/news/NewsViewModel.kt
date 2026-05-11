package com.example.restaurantapp.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.model.News
import com.example.restaurantapp.domain.model.Restaurant
import com.example.restaurantapp.domain.repository.NewsRepository
import com.example.restaurantapp.domain.repository.RestaurantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val restaurantsRepository: RestaurantsRepository,
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val selectedRestaurantId = MutableStateFlow<Int?>(null)

    val restaurants: StateFlow<List<Restaurant>> =
        restaurantsRepository.getRestaurants().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _news = MutableStateFlow<List<News>>(emptyList())
    val news: StateFlow<List<News>> = _news.asStateFlow()

    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> = _message.asSharedFlow()

    init {
        observeSelectedRestaurant()
    }

    fun selectRestaurant(restaurantId: Int?) {
        selectedRestaurantId.value = restaurantId
    }

    fun observeSelectedRestaurant() {
        viewModelScope.launch {
            selectedRestaurantId.collectLatest { restaurantId ->
                loadNews(restaurantId)
            }
        }
    }

    private suspend fun loadNews(restaurantId: Int?) {
        newsRepository.getNews(restaurantId)
            .onSuccess { news ->
                _news.value = news
            }.onFailure { error ->
                _message.emit(error.message ?: "Не удалось загрузить новости")
            }
    }
}