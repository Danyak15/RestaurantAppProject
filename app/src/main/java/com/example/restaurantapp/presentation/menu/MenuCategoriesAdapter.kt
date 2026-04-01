package com.example.restaurantapp.presentation.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.databinding.ItemMenuCategoryBinding
import com.example.restaurantapp.domain.model.Category

class MenuCategoriesAdapter(
    private val onItemClicked: (Category) -> Unit
    ) : ListAdapter<Category, MenuCategoriesAdapter.MenuCategoriesViewHolder>(CategoriesDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuCategoriesViewHolder {
        val binding = ItemMenuCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MenuCategoriesViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(
        holder: MenuCategoriesViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    class MenuCategoriesViewHolder(
        private val binding: ItemMenuCategoryBinding,
        private val onItemClicked: (Category) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.category = category
            binding.executePendingBindings()

            itemView.setOnClickListener {
                onItemClicked(category)
            }
        }
    }

    class CategoriesDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return oldItem == newItem
        }

    }
}