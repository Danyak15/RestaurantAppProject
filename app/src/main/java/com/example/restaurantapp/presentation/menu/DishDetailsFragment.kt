package com.example.restaurantapp.presentation.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.restaurantapp.data.repository.DishesRepositoryImpl
import com.example.restaurantapp.databinding.FragmentDishDetailsBinding
import com.example.restaurantapp.domain.repository.DishesRepository


class DishDetailsFragment : Fragment() {
    private var _binding: FragmentDishDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DishDetailsFragmentArgs by navArgs()
    private val viewModel: DishDetailsViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository: DishesRepository = DishesRepositoryImpl()
                return DishDetailsViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDishDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dishId = args.dishId

        viewModel.dish.observe(viewLifecycleOwner) { dish ->
            binding.dish = dish
            binding.executePendingBindings()
        }

        viewModel.loadDishDetails(dishId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}