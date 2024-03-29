package com.rodcollab.cliq.features.bookings.form.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.rodcollab.cliq.core.ui.R.*
import com.rodcollab.cliq.features.bookings.form.R
import com.rodcollab.cliq.features.bookings.form.databinding.FragmentBookingFormBinding
import com.rodcollab.cliq.utils.converters.Converter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class BookingFormFragment : Fragment() {

    private var _binding: FragmentBookingFormBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BookingFormViewModel
    private lateinit var viewModelSearchClient: SearchClientViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[BookingFormViewModel::class.java]
        viewModelSearchClient = ViewModelProvider(requireActivity())[SearchClientViewModel::class.java]
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
            binding.bookedAddressForm.text = it.clientSelected?.address
        }

        binding.toSelectDate.setOnTouchListener { _, motionEvent ->

            if (MotionEvent.ACTION_UP == motionEvent.action) {
                setupMaterialDatePicker()
            }
            true
        }

        saveNewBooking()
    }


    @SuppressLint("SimpleDateFormat")
    private fun setupMaterialDatePicker() {
        val builderDatePicker: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.datePicker()
                .setTextInputFormat(SimpleDateFormat("dd/MM/yyyy")).setCalendarConstraints(
                    calendarConstraints()
                )

        val pickerDate = builderDatePicker.build()

        pickerDate.show(this.parentFragmentManager, "DATE_PICKER")

        pickerDate.addOnPositiveButtonClickListener { dateInLong ->

            val builderTimePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
            val pickerTime = builderTimePicker.build()

            pickerTime.show(this.parentFragmentManager, "fragment_tag")

            viewModel.saveValueDate(dateInLong as Long)

            pickerTime.addOnPositiveButtonClickListener {

                binding.bookedTimeForm.text = formatTextTime(pickerTime.hour, pickerTime.minute)

            }

        }

    }

    private fun calendarConstraints() =
        CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now()).build()

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
            val bookedClientAddress = binding.bookedAddressForm.text.toString()
            val bookedTime = Converter.getValueTimeInLong(binding.bookedTimeForm.text)

            var bookedClientId = ""
            var bookedDate = ""
            viewModelSearchClient.clientSelected().observe(viewLifecycleOwner) { client ->
                bookedClientId = client.clientSelected?.id.toString()
            }
            viewModel.getValueDateSelected.observe(viewLifecycleOwner) { valueDateSelected ->
                bookedDate = valueDateSelected
            }
            viewModel.addBooking(bookedClientId, bookedClientName, bookedClientAddress, bookedDate, bookedTime)

            findNavController().navigate(com.rodcollab.cliq.core.ui.R.id.action_bookingForm_to_bookingList)
            Snackbar.make(requireActivity().findViewById(com.rodcollab.cliq.core.ui.R.id.navigationView), getString(
                            R.string.booking_successfully_registered), Snackbar.LENGTH_SHORT).show()

        }
    }
}