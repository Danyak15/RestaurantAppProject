package com.example.restaurantapp.presentation.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.restaurantapp.presentation.booking.BookFragment
import com.example.restaurantapp.presentation.info.InfoFragment
import com.example.restaurantapp.presentation.menu.MenuCategoriesFragment

class RestaurantPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val restaurantId: Int
) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> InfoFragment().apply {
                arguments = Bundle().apply {
                    putInt("restaurantId", restaurantId)
                }
            }
            1 -> MenuCategoriesFragment().apply {
                arguments = Bundle().apply {
                    putInt("restaurantId", restaurantId)
                }
            }
            2 -> BookFragment().apply {
                arguments = Bundle().apply {
                    putInt("restaurantId", restaurantId)
                }
            }
            else -> InfoFragment()
        }
    }

    override fun getItemCount(): Int = 3
}