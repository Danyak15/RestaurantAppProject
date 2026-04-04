package com.example.restaurantapp.presentation.menu

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantapp.R
import com.example.restaurantapp.databinding.FragmentMenuItemsBinding

class MenuItemsFragment : Fragment() {
    private lateinit var binding: FragmentMenuItemsBinding
    private val args: MenuItemsFragmentArgs by navArgs()
    private val viewModel: MenuItemsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuItemsBinding.inflate(inflater, container, false)
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
}