package com.example.restaurantapp.presentation.restaurants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantapp.data.repository.RestaurantsRepositoryImpl
import com.example.restaurantapp.databinding.FragmentRestaurantsBinding
import com.example.restaurantapp.domain.repository.RestaurantsRepository

class RestaurantsFragment : Fragment() {
    private var _binding: FragmentRestaurantsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RestaurantsViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository: RestaurantsRepository = RestaurantsRepositoryImpl()
                return RestaurantsViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRestaurantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RestaurantsAdapter { restaurant ->
            val action = RestaurantsFragmentDirections
                .actionRestaurantsFragmentToRestaurantDetailFragment(restaurant.id, restaurant.name)
            findNavController().navigate(action)
        }

        binding.recyclerViewRestaurants.adapter = adapter
        binding.recyclerViewRestaurants.layoutManager = LinearLayoutManager(context)

        viewModel.restaurants.observe(viewLifecycleOwner) { restaurants ->
            adapter.submitList(restaurants)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}