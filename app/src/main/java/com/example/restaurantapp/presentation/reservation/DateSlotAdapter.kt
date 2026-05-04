package com.example.restaurantapp.presentation.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.databinding.ItemDateSlotBinding

class DateSlotAdapter(
    private val onItemClick: (DateSlotModel) -> Unit
) : ListAdapter<DateSlotModel, DateSlotAdapter.DateSlotViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DateSlotViewHolder {
        val binding = ItemDateSlotBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DateSlotViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DateSlotViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DateSlotViewHolder(
        private val binding: ItemDateSlotBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dateSlot: DateSlotModel) {
            binding.dateSlot = dateSlot
            binding.executePendingBindings()

            itemView.setOnClickListener {
                onItemClick(dateSlot)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<DateSlotModel>() {
            override fun areItemsTheSame(oldItem: DateSlotModel, newItem: DateSlotModel) =
                oldItem.date == newItem.date

            override fun areContentsTheSame(oldItem: DateSlotModel, newItem: DateSlotModel) =
                oldItem == newItem
        }
    }
}