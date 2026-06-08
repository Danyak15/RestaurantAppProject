package com.example.restaurantapp.presentation.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.restaurantapp.presentation.reservation.ReservationFragment
import com.example.restaurantapp.presentation.info.InfoFragment
import com.example.restaurantapp.presentation.menu.MenuCategoriesFragment

class RestaurantPagerAdapter(
    fragment: Fragment,
    private val restaurantId: Long
) : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> InfoFragment().apply {
                arguments = Bundle().apply {
                    putLong("restaurantId", restaurantId)
                }
            }
            1 -> MenuCategoriesFragment().apply {
                arguments = Bundle().apply {
                    putLong("restaurantId", restaurantId)
                }
            }
            2 -> ReservationFragment().apply {
                arguments = Bundle().apply {
                    putLong("restaurantId", restaurantId)
                }
            }
            else -> InfoFragment()
        }
    }

    override fun getItemCount(): Int = 3
}