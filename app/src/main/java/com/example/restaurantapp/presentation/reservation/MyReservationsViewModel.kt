package com.example.restaurantapp.presentation.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.model.Reservation
import com.example.restaurantapp.domain.repository.ReservationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyReservationsViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository
) : ViewModel() {
    private val _reservations = MutableStateFlow<List<Reservation>>(emptyList())
    val reservations = _reservations.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    init {
        getMyReservations()
    }

    fun getMyReservations() {
        viewModelScope.launch {
            val result = reservationRepository.getMyReservations()

            result.onSuccess { reservations ->
                _reservations.value = reservations.filter { it.status == "ACTIVE" }
            }.onFailure { error ->
                _message.value = error.message ?: "Не удалось загрузить брони"
            }
        }
    }

    fun cancelReservation(id: Long) {
        viewModelScope.launch {
            val result = reservationRepository.cancelReservation(id)

            result.onSuccess {
                _reservations.value = _reservations.value.filter { it.id != id }
            }.onFailure { error ->
                _message.value = error.message ?: "Не удалось отменить бронь"
            }
        }
    }

    fun clearMessage() {
        _message.value = null
    }
}