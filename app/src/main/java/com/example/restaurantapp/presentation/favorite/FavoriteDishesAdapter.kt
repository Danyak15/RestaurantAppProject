package com.example.restaurantapp.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.databinding.ItemFavoriteDishBinding
import com.example.restaurantapp.domain.model.Dish

class FavoriteDishesAdapter(
    private val onItemClick: (Dish) -> Unit
) : ListAdapter<Dish, FavoriteDishesAdapter.FavoriteDishViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteDishViewHolder {
        val binding = ItemFavoriteDishBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return FavoriteDishViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteDishViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoriteDishViewHolder(
        private val binding: ItemFavoriteDishBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteDish: Dish) {
            binding.favoriteDish = favoriteDish
            binding.executePendingBindings()

            itemView.setOnClickListener {
                onItemClick(favoriteDish)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Dish>() {
            override fun areItemsTheSame(oldItem: Dish, newItem: Dish) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Dish, newItem: Dish) =
                oldItem == newItem
        }
    }
}