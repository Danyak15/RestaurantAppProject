package com.example.restaurantapp.presentation.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.restaurantapp.databinding.FragmentMenuCategoriesBinding
import com.example.restaurantapp.presentation.details.RestaurantDetailsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MenuCategoriesFragment : Fragment() {
    private var _binding: FragmentMenuCategoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuCategoriesViewModel by viewModels()
    private val categoriesAdapter: MenuCategoriesAdapter by lazy {
        MenuCategoriesAdapter { category ->
            val action = RestaurantDetailsFragmentDirections
                .actionRestaurantDetailsFragmentToDishesFragment(
                    category.id,
                    category.name
                )

            findNavController().navigate(action)
        }
    }
    private val dishesAdapter: DishesAdapter by lazy {
        DishesAdapter { dish ->
            val action = RestaurantDetailsFragmentDirections
                .actionRestaurantDetailsFragmentToDishDetailsFragment(dish.id)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val restaurantId = arguments?.getLong("restaurantId") ?: return
        
        setupRecyclerViews()
        setupSearchView()
        observeViewModel()
        viewModel.loadCategories(restaurantId)
    }

    private fun setupRecyclerViews() {
        binding.rvCategories.adapter = categoriesAdapter
        binding.rvFoundDishes.adapter = dishesAdapter
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.categories.collect { categories ->
                        categoriesAdapter.submitList(categories)
                    }
                }

                launch {
                    viewModel.searchText.collect {
                        updateSearchMode()
                    }
                }

                launch {
                    viewModel.foundDishes.collect { dishes ->
                        dishesAdapter.submitList(dishes)
                        updateSearchMode()
                    }
                }
            }
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchText: String?): Boolean {
                viewModel.onSearchTextChanged(searchText.orEmpty())
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearchTextChanged(newText.orEmpty())
                return true
            }
        })
    }

    private fun updateSearchMode() {
        val searchText = viewModel.searchText.value
        val dishes = viewModel.foundDishes.value

        val isSearchMode = searchText.isNotBlank()
        val hasResults = dishes.isNotEmpty()

        binding.rvCategories.isVisible = !isSearchMode
        binding.rvFoundDishes.isVisible = isSearchMode && hasResults
        binding.tvEmptySearch.isVisible = isSearchMode && !hasResults
    }

    override fun onDestroyView() {
        binding.rvFoundDishes.adapter = null
        binding.rvCategories.adapter = null
        _binding = null
        super.onDestroyView()
    }
}