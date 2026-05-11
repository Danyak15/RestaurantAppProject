package com.example.restaurantapp.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.restaurantapp.R
import com.example.restaurantapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!viewModel.checkAuth()) {
            findNavController().navigate(
                R.id.action_profileFragment_to_loginFragment
            )
            return
        }

        setupClicks()
        observeViewModel()

        viewModel.getMe()
    }

    private fun setupClicks() {
        binding.btnExit.setOnClickListener {
            viewModel.clearSession()
            findNavController().navigate(
                R.id.action_profileFragment_to_loginFragment
            )
        }

        binding.btnChange.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_personInfoFragment
            )
        }

        binding.btnFavorite.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_favoriteDishesFragment
            )
        }

        binding.btnReservations.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_myReservationsFragment
            )
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.user.collect { user ->
                        if (user != null) {
                            binding.tvPhone.text = user.phone
                            binding.tvPoints.text = user.loyaltyPoints.toString()
                            binding.tvLevel.text = user.loyaltyLevel
                        }
                    }
                }

                launch {
                    viewModel.message.collect { message ->
                        Toast.makeText(
                            requireContext(),
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}