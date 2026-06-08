package com.example.restaurantapp.domain.usecase.restaurant

import com.example.restaurantapp.domain.repository.RestaurantsRepository
import javax.inject.Inject

class GetRestaurantsUseCase @Inject constructor(
    private val restaurantsRepository: RestaurantsRepository
) {
    suspend operator fun invoke() = restaurantsRepository.getRestaurants()
}

class GetRestaurantByIdUseCase @Inject constructor(
    private val restaurantsRepository: RestaurantsRepository
) {
    suspend operator fun invoke(id: Long) = restaurantsRepository.getRestaurantById(id)
}
