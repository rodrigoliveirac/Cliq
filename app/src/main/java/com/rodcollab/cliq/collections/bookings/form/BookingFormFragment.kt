package com.rodcollab.cliq.collections.bookings.form

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.rodcollab.cliq.collections.clients.domain.GetClientsUseCaseImpl
import com.rodcollab.cliq.collections.clients.form.SearchClientListAdapter
import com.rodcollab.cliq.core.Utils
import com.rodcollab.cliq.core.repository.BookingRepositoryImpl
import com.rodcollab.cliq.core.repository.ClientRepositoryImpl
import com.rodcollab.cliq.databinding.FragmentBookingFormBinding
import java.util.*

class BookingFormFragment : Fragment() {

    private var _binding: FragmentBookingFormBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SearchClientListAdapter

    private val viewModel: BookingFormViewModel by activityViewModels {

        val (bookingsRepository, onQueryTextChangeUseCase) = injection()
        BookingFormViewModel.Factory(onQueryTextChangeUseCase, bookingsRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SearchClientListAdapter(viewModel)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingFormBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clientSelected.observe(viewLifecycleOwner) { clientSelected ->
            if (clientSelected == true) {

                binding.cardViewClient.visibility = View.VISIBLE
                binding.line.visibility = View.VISIBLE

                binding.toSelectDate.visibility = View.VISIBLE

                binding.saveButton.visibility = View.VISIBLE

                binding.searchViewClients.visibility = View.GONE
                binding.searchViewRecyclerView.visibility = View.GONE
            }
        }

        viewModel.liveName.observe(viewLifecycleOwner) {
            binding.bookedClientName.text = it

        }

        binding.toSelectDate.setOnTouchListener { _, motionEvent ->

            if (MotionEvent.ACTION_UP == motionEvent.action) {
                setupMaterialDatePicker()
            }
            true
        }

        viewModel.clientSelected.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.searchViewRecyclerView.visibility = View.GONE
                binding.searchViewClients.visibility = View.GONE
            }
        }

        setupSearchClientListAdapter()
        updateListAccordingToOnQueryChanged()
        saveNewBooking()
    }

    private fun setupMaterialDatePicker() {
        val builderDatePicker: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.datePicker()
        //.setTextInputFormat(SimpleDateFormat("dd/MM/yyyy"))
        val pickerDate = builderDatePicker.build()
        pickerDate.show(this.parentFragmentManager, "DATE_PICKER")

        pickerDate.addOnPositiveButtonClickListener {

            val builderTimePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
            val pickerTime = builderTimePicker.build()

            pickerTime.show(this.parentFragmentManager, "fragment_tag")

            viewModel.saveValueDate(pickerDate.headerText)

            pickerTime.addOnPositiveButtonClickListener {
                viewModel.getValueDate.observe(viewLifecycleOwner) { date ->
                    binding.bookedDateForm.text = date
                }


                binding.bookedTimeForm.text = formatTextTime(pickerTime.hour, pickerTime.minute)

                binding.bookedDateForm.visibility = View.VISIBLE
                binding.bookedTimeForm.visibility = View.VISIBLE
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun formatTextTime(hour: Int, min: Int): String {
        return java.lang.String.format(
            Locale.getDefault(),
            "%02d:%02d",
            hour,
            min,
        )
    }

    private fun setupSearchClientListAdapter() {
        binding.searchViewRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchViewRecyclerView.adapter = adapter

        val divider = Utils(requireContext()).addingDividerDecoration()

        binding.searchViewRecyclerView.addItemDecoration(divider)

    }

    private fun updateListAccordingToOnQueryChanged() {
        binding.searchViewClients.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                viewModel.onQueryTextChange(newText.toString())

                return false
            }

        })

        viewModel.stateOnceAndStream().observe(viewLifecycleOwner) {
            adapter.submitList(it.clientList)
        }
    }

    private fun saveNewBooking() {
        binding.saveButton.setOnClickListener {
            val bookedClientName = binding.bookedClientName.text.toString()
            val bookedDate = binding.bookedDateForm.text.toString()

            val bookedTime =
                binding.bookedTimeForm.text.toString().split(":").mapIndexed { index, string ->
                    when (index) {
                        0 -> string.toLong() * 3600000L
                        else -> {
                            string.toLong() * 60000L
                        }
                    }
                }.sumOf { it }

            viewModel.addBooking(bookedClientName, bookedDate, bookedTime)

            findNavController().navigateUp()
            viewModel.bookingSaved(false)
        }
    }

    private fun injection(): Pair<BookingRepositoryImpl, OnQueryTextChangeUseCaseImpl> {
        val clientRepository = ClientRepositoryImpl
        val bookingsRepository = BookingRepositoryImpl
        val getClientsUseCase = GetClientsUseCaseImpl(clientRepository)
        val onQueryTextChangeUseCase = OnQueryTextChangeUseCaseImpl(getClientsUseCase)
        return Pair(bookingsRepository, onQueryTextChangeUseCase)
    }

}