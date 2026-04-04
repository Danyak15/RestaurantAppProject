package com.example.restaurantapp.presentation.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.restaurantapp.R
import com.example.restaurantapp.databinding.FragmentMenuItemBinding


class MenuItemFragment : Fragment() {
    private var _binding: FragmentMenuItemBinding? = null
    private val binding get() = _binding!!
    private val args: MenuItemFragmentArgs by navArgs()
    private val viewModel: MenuItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuItemId = args.menuItemId

        viewModel.menuItem.observe(viewLifecycleOwner) { menuItem ->
            binding.menuItem = menuItem
            binding.executePendingBindings()
        }

        viewModel.loadMenuItem(menuItemId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}