package com.example.restaurantapp.presentation.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.databinding.ItemNewsBinding
import com.example.restaurantapp.domain.model.News

class NewsAdapter(
    private val onItemClick: (News) -> Unit
) : ListAdapter<News, NewsAdapter.NewsViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NewsViewHolder(
        private val binding: ItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: News) {
            binding.news = item
            binding.executePendingBindings()

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean =
                oldItem == newItem

        }
    }
}