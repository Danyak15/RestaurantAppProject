package com.example.restaurantapp.presentation.menu

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.restaurantapp.databinding.FragmentDishesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DishesFragment : Fragment() {
    private var _binding: FragmentDishesBinding? = null
    private val binding get() = _binding!!
    private val args: DishesFragmentArgs by navArgs()
    private val viewModel: DishesViewModel by viewModels()
    private val adapter: DishesAdapter by lazy {
        DishesAdapter { dish ->
            val action = DishesFragmentDirections
                .actionDishesFragmentToDishDetailsFragment(dish.id)
            findNavController().navigate(action)
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
        binding.recyclerViewDishes.adapter = adapter

        observeViewModel()
        viewModel.loadDishes(categoryId)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dishes.collect { dishes ->
                    adapter.submitList(dishes)
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.recyclerViewDishes.adapter = null
        _binding = null
        super.onDestroyView()
    }
}