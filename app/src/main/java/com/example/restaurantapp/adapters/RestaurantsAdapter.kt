package com.example.restaurantapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.data.models.Restaurant
import com.example.restaurantapp.databinding.ItemRestaurantBinding

class RestaurantsAdapter(
    private val restaurants: List<Restaurant>,
    private val onItemClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantsAdapter.RestaurantViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantViewHolder {
        val binding = ItemRestaurantBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RestaurantViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(
        holder: RestaurantViewHolder,
        position: Int
    ) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount(): Int = restaurants.size

    class RestaurantViewHolder(
        private val binding: ItemRestaurantBinding,
        private val onItemClick: (Restaurant) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant) {
            binding.restaurant = restaurant
            binding.executePendingBindings()

            itemView.setOnClickListener {
                onItemClick(restaurant)
            }
        }
    }
}