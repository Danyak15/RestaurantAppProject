package com.example.restaurantapp.presentation.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.databinding.ItemMenuItemBinding
import com.example.restaurantapp.domain.model.MenuItem

class MenuItemsAdapter(
    private val onItemClick: (MenuItem) -> Unit
) : ListAdapter<MenuItem, MenuItemsAdapter.MenuItemViewHolder>(diffCallback){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuItemViewHolder {
        val binding = ItemMenuItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return MenuItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MenuItemViewHolder(
        private val binding: ItemMenuItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MenuItem) {
            binding.menuItem = item
            binding.executePendingBindings()

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<MenuItem>() {
            override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem) =
                oldItem == newItem
        }
    }
}
