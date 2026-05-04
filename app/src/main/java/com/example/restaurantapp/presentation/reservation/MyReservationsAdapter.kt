package com.example.restaurantapp.presentation.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.databinding.ItemReservationBinding
import com.example.restaurantapp.domain.model.Reservation

class MyReservationsAdapter(
    private val onItemClick: (Reservation) -> Unit
) : ListAdapter<Reservation, MyReservationsAdapter.ReservationsViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReservationsViewHolder {
        val binding = ItemReservationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReservationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReservationsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ReservationsViewHolder(
        private val binding: ItemReservationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reservation: Reservation) {
            binding.reservation = reservation
            binding.executePendingBindings()

            itemView.setOnClickListener {
                onItemClick(reservation)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Reservation>() {
            override fun areItemsTheSame(oldItem: Reservation, newItem: Reservation) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Reservation, newItem: Reservation) =
                oldItem == newItem
        }
    }
}
