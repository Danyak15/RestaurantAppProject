package com.example.restaurantapp.presentation.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantapp.RestaurantApplication
import com.example.restaurantapp.databinding.FragmentMenuCategoriesBinding
import com.example.restaurantapp.presentation.details.RestaurantDetailsFragmentDirections


class MenuCategoriesFragment : Fragment() {
    private var _binding: FragmentMenuCategoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuCategoriesViewModel by viewModels {
        val appContainer = (requireActivity().application as RestaurantApplication).appContainer
        MenuCategoriesViewModelFactory(
            appContainer.categoriesRepository,
            appContainer.dishesRepository
        )
    }
    private val categoriesAdapter by lazy {
        MenuCategoriesAdapter { category ->
            val action = RestaurantDetailsFragmentDirections
                .actionRestaurantDetailsFragmentToDishesFragment(category.id, category.name)
            findNavController().navigate(action)
        }
    }
    private val dishesAdapter by lazy {
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

        val restaurantId = arguments?.getInt("restaurantId") ?: 0

        binding.recyclerViewCategories.adapter = categoriesAdapter
        binding.rvFoundDishes.adapter = dishesAdapter
        binding.recyclerViewCategories.layoutManager = LinearLayoutManager(context)
        binding.rvFoundDishes.layoutManager = LinearLayoutManager(context)

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

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.submitList(categories)
        }

        viewModel.foundDishes.observe(viewLifecycleOwner) { dishes ->
            dishesAdapter.submitList(dishes)
            updateSearchMode()
        }

        viewModel.searchText.observe(viewLifecycleOwner) {
            updateSearchMode()
        }

        viewModel.loadCategories(restaurantId)
    }

    private fun updateSearchMode() {
        val searchText = viewModel.searchText.value.orEmpty()
        val dishes = viewModel.foundDishes.value.orEmpty()

        val isSearchMode = searchText.isNotBlank()
        val hasResults = dishes.isNotEmpty()

        binding.recyclerViewCategories.isVisible = !isSearchMode
        binding.rvFoundDishes.isVisible = isSearchMode && hasResults
        binding.tvEmptySearch.isVisible = isSearchMode && !hasResults
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}