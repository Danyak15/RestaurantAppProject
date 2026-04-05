package com.example.restaurantapp.presentation.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.data.repository.RestaurantsRepositoryImpl
import com.example.restaurantapp.databinding.FragmentInfoBinding
import com.example.restaurantapp.domain.repository.RestaurantsRepository
import com.example.restaurantapp.presentation.restaurants.RestaurantsViewModel

class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InfoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository: RestaurantsRepository = RestaurantsRepositoryImpl()
                return InfoViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val restaurantId = arguments?.getInt("restaurantId") ?: 0

        viewModel.restaurant.observe(viewLifecycleOwner) { restaurant ->
            binding.restaurant = restaurant
            binding.executePendingBindings()
        }

        viewModel.loadRestaurant(restaurantId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}