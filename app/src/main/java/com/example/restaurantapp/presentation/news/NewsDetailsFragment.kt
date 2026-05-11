package com.example.restaurantapp.presentation.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.restaurantapp.databinding.FragmentNewsDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsFragment : Fragment() {
    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: NewsDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.news = args.news
        binding.executePendingBindings()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}