package com.example.restaurantapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.restaurantapp.MainActivity
import com.example.restaurantapp.fragments.BookFragment
import com.example.restaurantapp.fragments.InfoFragment
import com.example.restaurantapp.fragments.MenuFragment

class RestaurantPagerAdapter(fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> InfoFragment()
            1 -> MenuFragment()
            2 -> BookFragment()
            else -> InfoFragment()
        }
    }

    override fun getItemCount(): Int = 3
}