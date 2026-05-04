package com.example.restaurantapp.presentation.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.domain.repository.ReservationRepository

class MyReservationsViewModelFactory(
    private val reservationRepository: ReservationRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyReservationsViewModel::class.java)) {
            return MyReservationsViewModel(reservationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}