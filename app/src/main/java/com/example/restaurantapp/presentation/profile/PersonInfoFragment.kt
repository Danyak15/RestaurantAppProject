package com.example.restaurantapp.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.restaurantapp.RestaurantApplication
import com.example.restaurantapp.databinding.FragmentPersonInfoBinding

class PersonInfoFragment : Fragment() {
    private var _binding: FragmentPersonInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels {
        val appContainer = (requireActivity().application as RestaurantApplication).appContainer
        ProfileViewModelFactory(appContainer.accountRepository, appContainer.sessionManager)
    }

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

        setupCLicks()
        observeViewModel()
    }

    private fun setupCLicks() {
        binding.btnUpdate.setOnClickListener {
            viewModel.updateMe(
                binding.etName.text.toString().trim(),
                binding.etSurname.text.toString().trim(),
                binding.etEmail.text.toString().trim()
            )
        }
    }

    private fun observeViewModel() {
        viewModel.toastMessage.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                viewModel.clearError()
            }
        }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.viewModel = viewModel
            }
        }

        viewModel.isUpdateSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                viewModel.clearSuccess()
                Toast.makeText(requireContext(), "Профиль успешно обновлен", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}