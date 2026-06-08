package com.example.restaurantapp.presentation.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.databinding.ItemRestaurantHoursBinding
import com.example.restaurantapp.domain.model.RestaurantWorkingHours
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter

class RestaurantHoursAdapter :
    ListAdapter<RestaurantWorkingHours, RestaurantHoursAdapter.RestaurantHoursViewHolder>(
        diffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantHoursViewHolder {
        val binding = ItemRestaurantHoursBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RestaurantHoursViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantHoursViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RestaurantHoursViewHolder(
        private val binding: ItemRestaurantHoursBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RestaurantWorkingHours) {
            binding.tvDay.text = item.dayOfWeek.shortName()
            binding.tvHours.text = if (item.isClosed || item.openTime == null || item.closeTime == null) {
                "Выходной"
            } else {
                "${item.openTime.format(timeFormatter)}-${item.closeTime.format(timeFormatter)}"
            }
        }
    }

    companion object {
        private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        private val diffCallback = object : DiffUtil.ItemCallback<RestaurantWorkingHours>() {
            override fun areItemsTheSame(
                oldItem: RestaurantWorkingHours,
                newItem: RestaurantWorkingHours
            ) = oldItem.dayOfWeek == newItem.dayOfWeek

            override fun areContentsTheSame(
                oldItem: RestaurantWorkingHours,
                newItem: RestaurantWorkingHours
            ) = oldItem == newItem
        }

        private fun DayOfWeek.shortName(): String {
            return when (this) {
                DayOfWeek.MONDAY -> "Пн"
                DayOfWeek.TUESDAY -> "Вт"
                DayOfWeek.WEDNESDAY -> "Ср"
                DayOfWeek.THURSDAY -> "Чт"
                DayOfWeek.FRIDAY -> "Пт"
                DayOfWeek.SATURDAY -> "Сб"
                DayOfWeek.SUNDAY -> "Вс"
            }
        }
    }
}
