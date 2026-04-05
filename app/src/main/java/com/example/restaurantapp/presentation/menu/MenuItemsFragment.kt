package com.example.restaurantapp.presentation.menu

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantapp.R
import com.example.restaurantapp.data.repository.MenuItemsRepositoryImpl
import com.example.restaurantapp.databinding.FragmentMenuItemsBinding
import com.example.restaurantapp.domain.repository.MenuItemsRepository

class MenuItemsFragment : Fragment() {
    private var _binding: FragmentMenuItemsBinding? = null
    private val binding get() = _binding!!
    private val args: MenuItemsFragmentArgs by navArgs()
    private val viewModel: MenuItemsViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository: MenuItemsRepository = MenuItemsRepositoryImpl()
                return MenuItemsViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryId = args.categoryId

        val adapter = MenuItemsAdapter { item ->
            val action = MenuItemsFragmentDirections
                .actionMenuItemsFragmentToMenuItemFragment(item.id)
            findNavController().navigate(action)
        }

        binding.recyclerViewMenuItems.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewMenuItems.adapter = adapter

        viewModel.menuItems.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }

        viewModel.loadMenuItems(categoryId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}