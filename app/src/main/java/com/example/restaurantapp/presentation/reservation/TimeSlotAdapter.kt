package com.example.restaurantapp.presentation.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.databinding.ItemTimeSlotBinding

class TimeSlotAdapter(
    private val onItemClick: (TimeSlotModel) -> Unit
) : ListAdapter<TimeSlotModel, TimeSlotAdapter.TimeSlotViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TimeSlotViewHolder {
        val binding = ItemTimeSlotBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TimeSlotViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeSlotViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TimeSlotViewHolder(
        private val binding: ItemTimeSlotBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(timeSlot: TimeSlotModel) {
            binding.timeSlot = timeSlot
            binding.executePendingBindings()

            itemView.setOnClickListener {
                onItemClick(timeSlot)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<TimeSlotModel>() {
            override fun areItemsTheSame(oldItem: TimeSlotModel, newItem: TimeSlotModel) =
                oldItem.time == newItem.time

            override fun areContentsTheSame(oldItem: TimeSlotModel, newItem: TimeSlotModel) =
                oldItem == newItem
        }
    }
}