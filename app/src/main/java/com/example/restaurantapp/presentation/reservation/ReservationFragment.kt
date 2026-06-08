package com.example.restaurantapp.presentation.reservation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.databinding.FragmentReservationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReservationFragment : Fragment() {
    private var _binding: FragmentReservationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReservationViewModel by viewModels()
    private val dateAdapter: DateSlotAdapter by lazy {
        DateSlotAdapter { date ->
            viewModel.selectDate(date)
        }
    }
    private val timeAdapter: TimeSlotAdapter by lazy {
        TimeSlotAdapter { time ->
            viewModel.selectTime(time)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val restaurantId = arguments?.getLong("restaurantId") ?: return
        viewModel.loadRestaurant(restaurantId)

        setupRecyclerViews()
        setupClicks()
        observeViewModel()
        observeMessage()
    }

    private fun setupRecyclerViews() {
        val layoutManager = binding.rvDatePicker.layoutManager as LinearLayoutManager

        binding.rvDatePicker.adapter = dateAdapter
        binding.rvTimePicker.adapter = timeAdapter

        binding.rvDatePicker.enforceHorizontalScroll()
        binding.rvTimePicker.enforceHorizontalScroll()

        binding.rvDatePicker.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val first = layoutManager.findFirstVisibleItemPosition()
                    val last = layoutManager.findLastVisibleItemPosition()

                    if (first == RecyclerView.NO_POSITION || last == RecyclerView.NO_POSITION) return

                    val center = (first + last) / 2
                    val month = dateAdapter.currentList.getOrNull(center)?.monthName

                    if (month != null) {
                        binding.tvMonth.text = month
                    }
                }
            }
        )
    }

    private fun setupClicks() {
        binding.btnPlus.setOnClickListener {
            viewModel.incrementGuests()
        }

        binding.btnMinus.setOnClickListener {
            viewModel.decrementGuests()
        }

        binding.btnCreateReservation.setOnClickListener {
            viewModel.createReservation()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.dateSlots.collect { slots ->
                        dateAdapter.submitList(slots)
                    }
                }

                launch {
                    viewModel.timeSlots.collect { slots ->
                        binding.rvTimePicker.isVisible = slots.isNotEmpty()
                        binding.tvEmptyTime.isVisible = slots.isEmpty()

                        val shouldResetScroll = timeAdapter.currentList.map { it.time } !=
                                slots.map { it.time }

                        timeAdapter.submitList(slots) {
                            if (shouldResetScroll) {
                                binding.rvTimePicker.scrollToPosition(0)
                            }
                        }
                    }
                }

                launch {
                    viewModel.isReady.collect { ready ->
                        binding.btnCreateReservation.isVisible = ready
                    }
                }

                launch {
                    viewModel.isLoading.collect { loading ->
                        binding.btnCreateReservation.isEnabled = !loading
                    }
                }

                launch {
                    viewModel.guests.collect { guests ->
                        binding.tvGuestsCount.text = guests.toString()
                    }
                }

                launch {
                    viewModel.restaurant.collect { restaurant ->
                        binding.restaurant = restaurant
                        binding.executePendingBindings()
                    }
                }

                launch {
                    viewModel.reservationSuccess.collect {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    private fun observeMessage() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.message.collect { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun RecyclerView.enforceHorizontalScroll() {
        addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(
                rv: RecyclerView,
                e: MotionEvent
            ): Boolean {
                if (e.action == MotionEvent.ACTION_DOWN && rv.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                    rv.parent.requestDisallowInterceptTouchEvent(true)
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
    }

    override fun onDestroyView() {
        binding.rvDatePicker.adapter = null
        binding.rvTimePicker.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
