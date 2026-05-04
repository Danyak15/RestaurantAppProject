package com.example.restaurantapp.presentation.reservation

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

data class DateSlotModel(
    val date: LocalDate,
    val isSelected: Boolean = false,
    val isWeekend: Boolean = (date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY)
) {
    val monthName: String
        get() = date.month.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("ru"))
            .replaceFirstChar { it.uppercase() }

    val dayOfWeekName: String
        get() = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("ru"))
            .replaceFirstChar { it.uppercase() }

    val dayOfMonth: String
        get() = date.dayOfMonth.toString()
}