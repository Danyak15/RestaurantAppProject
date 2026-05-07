package com.example.restaurantapp.presentation.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.restaurantapp.databinding.FragmentFavoriteDishesBinding
import com.example.restaurantapp.domain.model.Restaurant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteDishesFragment : Fragment() {
    private var _binding: FragmentFavoriteDishesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteDishesViewModel by viewModels()
    private val favoritesAdapter: FavoriteDishesAdapter by lazy {
        FavoriteDishesAdapter { dish ->
            val action = FavoriteDishesFragmentDirections
                .actionFavoriteDishesFragmentToDishDetailsFragment(dish.id)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteDishesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeFavoriteDishes()
        observeRestaurantsSpinner()
    }

    private fun setupRecyclerView() {
        binding.rvFavoriteDishes.apply {
            layoutManager = GridLayoutManager(context, 2)
            this.adapter = favoritesAdapter
        }
    }

    private fun observeFavoriteDishes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteDishes.collect { dishes ->
                    favoritesAdapter.submitList(dishes)

                    binding.rvFavoriteDishes.isVisible = dishes.isNotEmpty()
                    binding.spinnerRestaurants.isVisible = dishes.isNotEmpty()
                    binding.tvNoFavorites.isVisible = dishes.isEmpty()
                }
            }
        }
    }

    private fun observeRestaurantsSpinner() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteRestaurants.collect { restaurants ->
                    setupRestaurantsSpinner(restaurants)
                }
            }
        }
    }

    private fun setupRestaurantsSpinner(restaurants: List<Restaurant>) {
        val items = listOf("Все рестораны") + restaurants.map { it.name }

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            items
        )

        binding.spinnerRestaurants.adapter = spinnerAdapter

        binding.spinnerRestaurants.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        viewModel.selectRestaurant(null)
                    } else {
                        val selectedRestaurant = restaurants[position - 1]
                        viewModel.selectRestaurant(selectedRestaurant.id)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
    }

    override fun onDestroyView() {
        binding.rvFavoriteDishes.adapter = null
        _binding = null
        super.onDestroyView()
    }
}