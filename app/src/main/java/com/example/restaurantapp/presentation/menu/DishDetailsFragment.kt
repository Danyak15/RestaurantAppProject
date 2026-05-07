package com.example.restaurantapp.presentation.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.restaurantapp.databinding.FragmentDishDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DishDetailsFragment : Fragment() {
    private var _binding: FragmentDishDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DishDetailsFragmentArgs by navArgs()

    private val viewModel: DishDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dishId = args.dishId
        binding.btnFavorite.isVisible = viewModel.isFavoriteVisible

        observeViewModel()
        setupClicks()
        viewModel.loadDishDetails(dishId)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.dish.collect { dish ->
                        binding.dish = dish
                        binding.executePendingBindings()
                    }
                }

                launch {
                    viewModel.isFavorite.collect { isFavorite ->
                        binding.btnFavorite.isSelected = isFavorite
                    }
                }
            }
        }
    }

    private fun setupClicks() {
        binding.btnFavorite.setOnClickListener {
            viewModel.toggleFavorite()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}