package com.example.restaurantapp.presentation.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.model.Reservation
import com.example.restaurantapp.domain.usecase.reservation.CancelReservationUseCase
import com.example.restaurantapp.domain.usecase.reservation.GetMyReservationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyReservationsViewModel @Inject constructor(
    private val getMyReservationsUseCase: GetMyReservationsUseCase,
    private val cancelReservationUseCase: CancelReservationUseCase
) : ViewModel() {
    private val _reservations = MutableStateFlow<List<Reservation>>(emptyList())
    val reservations = _reservations.asStateFlow()

    private val _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()

    init {
        getMyReservations()
    }

    fun getMyReservations() {
        viewModelScope.launch {
            getMyReservationsUseCase()
                .onSuccess { reservations ->
                    _reservations.value = reservations
                }.onFailure { error ->
                    _message.emit(error.message ?: "Не удалось загрузить брони")
                }
        }
    }

    fun cancelReservation(id: Long) {
        viewModelScope.launch {
            cancelReservationUseCase(id)
                .onSuccess {
                    _reservations.value = _reservations.value.filter { it.id != id }
                }.onFailure { error ->
                    _message.emit(error.message ?: "Не удалось отменить бронь")
                }
        }
    }
}
