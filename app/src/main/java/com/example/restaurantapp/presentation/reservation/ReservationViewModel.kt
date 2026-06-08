package com.example.restaurantapp.presentation.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.domain.model.Restaurant
import com.example.restaurantapp.domain.usecase.reservation.CreateReservationUseCase
import com.example.restaurantapp.domain.usecase.reservation.GetAvailableTimesUseCase
import com.example.restaurantapp.domain.usecase.restaurant.GetRestaurantByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val createReservationUseCase: CreateReservationUseCase,
    private val getAvailableTimesUseCase: GetAvailableTimesUseCase,
    private val getRestaurantByIdUseCase: GetRestaurantByIdUseCase
) : ViewModel() {
    private val _restaurant = MutableStateFlow<Restaurant?>(null)
    val restaurant: StateFlow<Restaurant?> = _restaurant.asStateFlow()

    private val _guests = MutableStateFlow(1)
    val guests: StateFlow<Int> = _guests.asStateFlow()

    private val _dateSlots = MutableStateFlow<List<DateSlotModel>>(emptyList())
    val dateSlots: StateFlow<List<DateSlotModel>> = _dateSlots.asStateFlow()

    private val _timeSlots = MutableStateFlow<List<TimeSlotModel>>(emptyList())
    val timeSlots: StateFlow<List<TimeSlotModel>> = _timeSlots.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _reservationSuccess = MutableSharedFlow<Unit>()
    val reservationSuccess: SharedFlow<Unit> = _reservationSuccess.asSharedFlow()

    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> = _message.asSharedFlow()

    private val selectedDate = MutableStateFlow<LocalDate?>(null)
    private val selectedTime = MutableStateFlow<LocalTime?>(null)

    val isReady = combine(selectedDate, selectedTime) { date, time ->
        date != null && time != null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    init {
        generateDateSlots()
    }

    fun loadRestaurant(id: Long) {
        viewModelScope.launch {
            _restaurant.value = getRestaurantByIdUseCase(id)

            selectedDate.value?.let { date ->
                loadTimeSlots(date)
            }
        }
    }

    fun selectDate(slot: DateSlotModel) {
        selectedDate.value = slot.date

        _dateSlots.value = _dateSlots.value.map {
            it.copy(isSelected = it.date == slot.date)
        }

        selectedTime.value = null
        loadTimeSlots(slot.date)
    }

    fun selectTime(slot: TimeSlotModel) {
        selectedTime.value = slot.time
        _timeSlots.value = _timeSlots.value.map {
            it.copy(isSelected = it.time == slot.time)
        }
    }

    fun incrementGuests() {
        if (_guests.value < 8) {
            _guests.value++
            selectedDate.value?.let { loadTimeSlots(it) }
        }
    }

    fun decrementGuests() {
        if (_guests.value > 1) {
            _guests.value--
            selectedDate.value?.let { loadTimeSlots(it) }
        }
    }

    fun createReservation() {
        val guests = guests.value
        val date = selectedDate.value ?: return
        val time = selectedTime.value ?: return
        val restaurantId = restaurant.value?.id ?: return

        viewModelScope.launch {
            _isLoading.value = true

            val result = createReservationUseCase(
                restaurantId = restaurantId,
                date = date,
                time = time,
                guests = guests
            )

            result.onSuccess {
                _message.emit("Стол успешно забронирован")
                _reservationSuccess.emit(Unit)
            }.onFailure { error ->
                _message.emit(error.message ?: "Ошибка бронирования")
            }

            _isLoading.value = false
        }
    }

    private fun generateDateSlots() {
        val today = LocalDate.now()
        val dates = (0..60).map {
            DateSlotModel(
                date = today.plusDays(it.toLong()),
                isSelected = it == 0
            )
        }
        _dateSlots.value = dates
        selectedDate.value = today
    }

    private fun loadTimeSlots(date: LocalDate) {
        val restaurant = restaurant.value ?: return
        val guestsCount = guests.value

        viewModelScope.launch {
            getAvailableTimesUseCase(
                restaurant = restaurant,
                date = date,
                guests = guestsCount
            ).onSuccess { times ->
                _timeSlots.value = times.map { time ->
                    TimeSlotModel(time = time)
                }
                selectedTime.value = null
            }.onFailure { error ->
                _message.emit(error.message ?: "Ошибка при получении времени")
                _timeSlots.value = emptyList()
                selectedTime.value = null
            }
        }
    }
}
