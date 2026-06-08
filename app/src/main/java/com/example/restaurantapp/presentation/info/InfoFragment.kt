package com.example.restaurantapp.presentation.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantapp.databinding.FragmentInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InfoViewModel by viewModels()
    private val workingHoursAdapter = RestaurantHoursAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val restaurantId = arguments?.getLong("restaurantId") ?: return

        setupWorkingHours()
        observeViewModel()
        viewModel.loadRestaurant(restaurantId)
    }

    private fun setupWorkingHours() {
        binding.rvWorkingHours.adapter = workingHoursAdapter
        binding.rvWorkingHours.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun setupCallButton(phone: String?) {
        val hasPhone = !phone.isNullOrBlank()

        binding.btnCall.visibility = if (hasPhone) {
            View.VISIBLE
        } else {
            View.GONE
        }

        if (hasPhone) {
            binding.btnCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.fromParts("tel", phone, null)
                }

                startActivity(intent)
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.restaurant.collect { restaurant ->
                    binding.restaurant = restaurant
                    workingHoursAdapter.submitList(
                        restaurant?.workingHours ?: emptyList()
                    )

                    setupCallButton(restaurant?.phone)

                    binding.executePendingBindings()
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.rvWorkingHours.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
