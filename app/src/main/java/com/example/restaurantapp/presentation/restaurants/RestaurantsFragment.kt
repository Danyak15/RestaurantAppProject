package com.example.restaurantapp.presentation.restaurants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantapp.R
import com.example.restaurantapp.presentation.restaurants.RestaurantsAdapter
import com.example.restaurantapp.data.mock.MockData
import com.example.restaurantapp.databinding.FragmentRestaurantsBinding

class RestaurantsFragment : Fragment() {
    private lateinit var binding: FragmentRestaurantsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mockRestaurants = MockData.getRestaurants()
        binding.recyclerViewRestaurants.layoutManager = LinearLayoutManager(context)
        val adapter = RestaurantsAdapter(mockRestaurants) {
            findNavController().navigate(R.id.action_restaurantsFragment_to_restaurantDetailFragment)
        }
        binding.recyclerViewRestaurants.adapter = adapter
    }
}