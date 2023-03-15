package com.rodcollab.cliq.collections.bookings.form

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.rodcollab.cliq.collections.clients.domain.GetClientsUseCaseImpl
import com.rodcollab.cliq.core.ConversionUtils
import com.rodcollab.cliq.core.repository.BookingRepositoryImpl
import com.rodcollab.cliq.core.repository.ClientRepositoryImpl
import com.rodcollab.cliq.databinding.FragmentBookingFormBinding
import java.util.*

class BookingFormFragment : Fragment() {

    private var _binding: FragmentBookingFormBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookingFormViewModel by activityViewModels {
        val bookingsRepository = injection()
        BookingFormViewModel.Factory(bookingsRepository)
    }

    private val viewModelSearchClient: SearchClientViewModel by activityViewModels {
        val (getClientsUseCase, onQueryTextChangeUseCase) = injectionSearchClientViewModel()
        SearchClientViewModel.Factory(getClientsUseCase, onQueryTextChangeUseCase)
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

        viewModelSearchClient.clientSelected().observe(viewLifecycleOwner) {
            binding.bookedClientName.text = it.clientSelected?.name
        }

        binding.toSelectDate.setOnTouchListener { _, motionEvent ->

            if (MotionEvent.ACTION_UP == motionEvent.action) {
                setupMaterialDatePicker()
            }
            true
        }

        saveNewBooking()
    }

    private fun setupMaterialDatePicker() {
        val builderDatePicker: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.datePicker()

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

    private fun saveNewBooking() {
        binding.saveButton.setOnClickListener {

            val bookedClientName = binding.bookedClientName.text.toString()
            val bookedDate = binding.bookedDateForm.text.toString()
            val bookedTime = ConversionUtils.getValueTimeInLong(binding.bookedTimeForm.text)
            var bookedClientId = ""
            viewModelSearchClient.clientSelected().observe(viewLifecycleOwner) { client ->
                bookedClientId = client.clientSelected?.id.toString()
            }
            viewModel.addBooking(bookedClientId,bookedClientName, bookedDate, bookedTime)
            viewModelSearchClient.resetClientSelected()
            findNavController().navigateUp()
        }
    }


    private fun injection(): BookingRepositoryImpl {
        return BookingRepositoryImpl
    }

    private fun injectionSearchClientViewModel(): Pair<GetClientsUseCaseImpl, OnQueryTextChangeUseCaseImpl> {
        val clientRepository = ClientRepositoryImpl
        val getClientsUseCase = GetClientsUseCaseImpl(clientRepository)
        val onQueryTextChangeUseCase = OnQueryTextChangeUseCaseImpl(getClientsUseCase)

        return Pair(getClientsUseCase, onQueryTextChangeUseCase)
    }

}