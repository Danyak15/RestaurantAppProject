package com.example.restaurantapp.domain.usecase.reservation

import com.example.restaurantapp.domain.model.Reservation
import com.example.restaurantapp.domain.model.Restaurant
import com.example.restaurantapp.domain.repository.ReservationRepository
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class GetMyReservationsUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository
) {
    suspend operator fun invoke() = reservationRepository.getMyReservations()
        .map { reservations ->
            reservations.filter { it.status == "ACTIVE" }
        }
}

class CreateReservationUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository
) {
    suspend operator fun invoke(
        restaurant: Restaurant,
        date: LocalDate,
        time: LocalTime,
        guests: Int
    ): Result<Reservation> {
        if (guests !in restaurant.minGuests..restaurant.maxGuests) {
            return Result.failure(IllegalArgumentException("Недоступное количество гостей"))
        }

        return reservationRepository.createReservation(
            restaurantId = restaurant.id,
            dateTime = date.atTime(time).toString(),
            guests = guests
        )
    }
}

class GetInitialGuestsCountUseCase @Inject constructor() {
    operator fun invoke(restaurant: Restaurant) = restaurant.minGuests
}

class ChangeGuestsCountUseCase @Inject constructor() {
    operator fun invoke(
        restaurant: Restaurant,
        currentGuests: Int,
        delta: Int
    ) = (currentGuests + delta).coerceIn(
        minimumValue = restaurant.minGuests,
        maximumValue = restaurant.maxGuests
    )
}

class CancelReservationUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository
) {
    suspend operator fun invoke(id: Long) = reservationRepository.cancelReservation(id)
}

class GetAvailableTimesUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository
) {
    suspend operator fun invoke(
        restaurant: Restaurant,
        date: LocalDate,
        guests: Int
    ): Result<List<LocalTime>> {
        val hours = restaurant.workingHours.first { it.dayOfWeek == date.dayOfWeek }

        if (hours.isClosed) {
            return Result.success(emptyList())
        }

        val openTime = hours.openTime!!
        val closeTime = hours.closeTime!!

        return reservationRepository.getAvailableTimes(
            restaurantId = restaurant.id,
            date = date.toString(),
            guests = guests
        ).map { times ->
            times.map { LocalTime.parse(it) }
                .filter { time ->
                    !time.isBefore(openTime) && time.isBefore(closeTime)
                }
        }
    }
}
