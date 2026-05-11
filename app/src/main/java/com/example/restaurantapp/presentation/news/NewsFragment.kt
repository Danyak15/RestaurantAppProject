package com.example.restaurantapp.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.restaurantapp.databinding.FragmentNewsBinding
import com.example.restaurantapp.domain.model.Restaurant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()
    private val adapter: NewsAdapter by lazy {
        NewsAdapter { news ->
            val action = NewsFragmentDirections
                .actionNewsFragmentToNewsDetailsFragment(news)

            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.rvNews.adapter = adapter
    }

    private fun setupSpinner(restaurants: List<Restaurant>) {
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
                        val restaurant = restaurants[position - 1]
                        viewModel.selectRestaurant(restaurant.id)
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) = Unit
            }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.restaurants.collect { restaurants ->
                        setupSpinner(restaurants)
                    }
                }

                launch {
                    viewModel.news.collect { news ->
                        adapter.submitList(news)
                    }
                }

                launch {
                    viewModel.message.collect { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.rvNews.adapter = null
        _binding = null
        super.onDestroyView()
    }
}