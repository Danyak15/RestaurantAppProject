package com.example.restaurantapp.presentation.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.restaurantapp.data.repository.RestaurantsRepository
import com.example.restaurantapp.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем ID ресторана
        val restaurantId = arguments?.getInt("restaurantId") ?: 0

        // Загружаем ресторан
        val restaurant = RestaurantsRepository().getRestaurantById(restaurantId)

        binding.textView.text = restaurant?.name
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}