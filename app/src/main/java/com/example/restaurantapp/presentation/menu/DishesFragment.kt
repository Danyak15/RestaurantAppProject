package com.example.restaurantapp.presentation.menu

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantapp.data.repository.DishesRepositoryImpl
import com.example.restaurantapp.databinding.FragmentDishesBinding
import com.example.restaurantapp.domain.repository.DishesRepository

class DishesFragment : Fragment() {
    private var _binding: FragmentDishesBinding? = null
    private val binding get() = _binding!!
    private val args: DishesFragmentArgs by navArgs()
    private val viewModel: DishesViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository: DishesRepository = DishesRepositoryImpl()
                return DishesViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryId = args.categoryId

        val adapter = DishesAdapter { dish ->
            val action = DishesFragmentDirections
                .actionDishesFragmentToDishDetailsFragment(dish.id)
            findNavController().navigate(action)
        }

        binding.recyclerViewDishes.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewDishes.adapter = adapter

        viewModel.dishes.observe(viewLifecycleOwner) { dishes ->
            adapter.submitList(dishes)
        }

        viewModel.loadDishes(categoryId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}