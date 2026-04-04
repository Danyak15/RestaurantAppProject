package com.example.restaurantapp.presentation.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.databinding.ItemMenuCategoryBinding
import com.example.restaurantapp.domain.model.Category

class MenuCategoriesAdapter(
    private val onItemClick: (Category) -> Unit
    ) : ListAdapter<Category, MenuCategoriesAdapter.MenuCategoriesViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuCategoriesViewHolder {
        val binding = ItemMenuCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MenuCategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuCategoriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MenuCategoriesViewHolder(
        private val binding: ItemMenuCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.category = category
            binding.executePendingBindings()

            itemView.setOnClickListener {
                onItemClick(category)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Category, newItem: Category) =
                oldItem == newItem
        }
    }
}