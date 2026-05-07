package com.example.restaurantapp.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.restaurantapp.databinding.FragmentPersonInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonInfoFragment : Fragment() {
    private var _binding: FragmentPersonInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMe()

        setupClicks()
        observeViewModel()
    }

    private fun setupClicks() {
        binding.btnUpdate.setOnClickListener {
            viewModel.updateMe(
                name = binding.etName.text.toString().trim(),
                surname = binding.etSurname.text.toString().trim(),
                email = binding.etEmail.text.toString().trim().takeIf { it.isNotBlank() }
            )
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.updateSuccess.collect {
                        Toast.makeText(
                            requireContext(),
                            "Профиль успешно обновлен",
                            Toast.LENGTH_SHORT
                        ).show()

                        findNavController().popBackStack()
                    }
                }

                launch {
                    viewModel.user.collect { user ->
                        if (user != null) {
                            binding.user = user
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