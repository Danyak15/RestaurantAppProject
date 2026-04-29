package com.example.restaurantapp.presentation.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.databinding.ItemDishBinding
import com.example.restaurantapp.domain.model.Dish

class DishesAdapter(
    private val onItemClick: (Dish) -> Unit
) : ListAdapter<Dish, DishesAdapter.DishViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DishViewHolder {
        val binding = ItemDishBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return DishViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DishViewHolder(
        private val binding: ItemDishBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dish: Dish) {
            binding.dish = dish
            binding.executePendingBindings()

            itemView.setOnClickListener {
                onItemClick(dish)
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
