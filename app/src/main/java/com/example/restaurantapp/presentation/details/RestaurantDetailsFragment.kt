package com.example.restaurantapp.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.restaurantapp.databinding.FragmentRestaurantDetailsBinding
import com.google.android.material.tabs.TabLayoutMediator

class RestaurantDetailsFragment : Fragment() {
    private var _binding: FragmentRestaurantDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: RestaurantDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val restaurantId = args.restaurantId
        binding.viewPager.adapter = RestaurantPagerAdapter(this, restaurantId)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Информация"
                1 -> "Меню"
                2 -> "Бронирование"
                else -> "Информация"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}