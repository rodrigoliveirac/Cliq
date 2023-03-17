package com.rodcollab.cliq.collections.bookings.form

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.rodcollab.cliq.collections.clients.domain.GetClientsUseCaseImpl
import com.rodcollab.cliq.core.ConversionUtils
import com.rodcollab.cliq.core.database.AppDatabase
import com.rodcollab.cliq.core.repository.BookingRepositoryImpl
import com.rodcollab.cliq.core.repository.ClientRepositoryImpl
import com.rodcollab.cliq.databinding.FragmentBookingFormBinding
import java.text.SimpleDateFormat
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

    @RequiresApi(Build.VERSION_CODES.O)
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


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    private fun setupMaterialDatePicker() {
        val builderDatePicker: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.datePicker()
                .setTextInputFormat(SimpleDateFormat("dd/MM/yyyy"))

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
            val bookedTime = ConversionUtils.getValueTimeInLong(binding.bookedTimeForm.text)

            var bookedClientId = ""
            var bookedDate = ""
            viewModelSearchClient.clientSelected().observe(viewLifecycleOwner) { client ->
                bookedClientId = client.clientSelected?.id.toString()
            }
            viewModel.getValueDateSelected.observe(viewLifecycleOwner) { valueDateSelected ->
                bookedDate = valueDateSelected
            }
            viewModel.addBooking(bookedClientId, bookedClientName, bookedClientAddress, bookedDate, bookedTime)
            viewModelSearchClient.resetClientSelected()
            findNavController().navigateUp()
        }
    }


    private fun injection(): BookingRepositoryImpl {
        val db = AppDatabase.getInstance(requireContext())
        return BookingRepositoryImpl(db)
    }

    private fun injectionSearchClientViewModel(): Pair<GetClientsUseCaseImpl, OnQueryTextChangeUseCaseImpl> {
        val db = AppDatabase.getInstance(requireContext())
        val clientRepository = ClientRepositoryImpl(db)
        val getClientsUseCase = GetClientsUseCaseImpl(clientRepository)
        val onQueryTextChangeUseCase = OnQueryTextChangeUseCaseImpl(getClientsUseCase)

        return Pair(getClientsUseCase, onQueryTextChangeUseCase)
    }

}