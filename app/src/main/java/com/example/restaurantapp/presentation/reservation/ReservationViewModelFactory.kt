package com.example.restaurantapp.presentation.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.domain.repository.ReservationRepository
import com.example.restaurantapp.domain.repository.RestaurantsRepository

class ReservationViewModelFactory(
    private val reservationRepository: ReservationRepository,
    private val restaurantsRepository: RestaurantsRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReservationViewModel::class.java)) {
            return ReservationViewModel(
                reservationRepository,
                restaurantsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}