package com.example.restaurantapp.presentation.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantapp.databinding.FragmentMenuCategoriesBinding
import com.example.restaurantapp.presentation.details.RestaurantDetailsFragmentDirections


class MenuCategoriesFragment : Fragment() {
    private var _binding: FragmentMenuCategoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuCategoriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val restaurantId = arguments?.getInt("restaurantId") ?: 0

        val adapter = MenuCategoriesAdapter { category ->
            val action = RestaurantDetailsFragmentDirections
                .actionRestaurantDetailsFragmentToMenuItemsFragment(category.id)
            findNavController().navigate(action)
        }

        binding.recyclerViewCategories.adapter = adapter
        binding.recyclerViewCategories.layoutManager = LinearLayoutManager(context)

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            adapter.submitList(categories)
        }

        viewModel.loadCategories(restaurantId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}