package com.rodcollab.cliq.features.bookings.collections.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.rodcollab.cliq.core.ui.R
import com.rodcollab.cliq.features.bookings.collections.databinding.FragmentBookingListBinding
import com.rodcollab.cliq.features.bookings.collections.ui.adapters.BookingsAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate

@SuppressLint("ClickableViewAccessibility", "SimpleDateFormat")
@AndroidEntryPoint
class BookingListFragment : Fragment() {

    private var _binding: FragmentBookingListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: BookingsAdapter

    private lateinit var viewModel: BookingListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[BookingListViewModel::class.java]
        lifecycle.addObserver(BookingListLifecycleObserver(viewModel))
        adapter = BookingsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDateTextHeader()

        setupAdapter()

        menuCreate()

        observeList()
    }

    private fun setupDateTextHeader() {
        viewModel.stateOnceAndStream().observe(viewLifecycleOwner) {
            binding.date.text = it.textDate
        }

        binding.arrowBack.setOnClickListener { viewModel.onArrowBack(requireContext()) }
        binding.arrowForward.setOnClickListener { viewModel.onArrowForward(requireContext()) }

        toSelectDate()
    }

    private fun toSelectDate() {
        binding.date.setOnTouchListener { _, motionEvent ->
            if (MotionEvent.ACTION_UP == motionEvent.action) {
                val builder: MaterialDatePicker.Builder<*> = MaterialDatePicker.Builder.datePicker()
                    .setTextInputFormat(SimpleDateFormat(LocalDate.now().toString()))

                val pickerDate = builder.build()

                pickerDate.show(this.parentFragmentManager, "DATE_PICKER")

                pickerDate.addOnPositiveButtonClickListener {
                    val date = it as Long
                    viewModel.pickDate(requireContext(), date)
                }
            }
            true
        }
    }

    private fun observeList() {
        viewModel.stateOnceAndStream().observe(viewLifecycleOwner) { uiState ->
            bindUiState(uiState)
        }
    }

    private fun setupAdapter() {
        binding.bookingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.bookingRecyclerView.adapter = adapter
    }

    private fun menuCreate() {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.add_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.overflowMenu) {
                    findNavController().navigate(R.id.action_bookingList_to_searchClientList)
                    return true
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun bindUiState(uiState: BookingListViewModel.UiState) {
        adapter.submitList(uiState.bookingList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}