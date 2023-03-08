package com.rodcollab.cliq.collections.bookings.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rodcollab.cliq.core.repository.BookingRepositoryImpl
import com.rodcollab.cliq.databinding.FragmentBookingFormBinding

class BookingFormFragment : Fragment() {

    private var _binding: FragmentBookingFormBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookingFormViewModel by activityViewModels {

        val bookingsRepository = BookingRepositoryImpl
        BookingFormViewModel.Factory(bookingsRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveNewBooking()
    }

    private fun saveNewBooking() {
        binding.saveButton.setOnClickListener {
            val bookedTime = binding.bookingTimeTextInput.editText?.text.toString()
            val bookedClientName = binding.clientNameTextInput.editText?.text.toString()
            val bookedDate = binding.bookingDateTextInput.editText?.text.toString()

            viewModel.addBooking(bookedTime, bookedClientName, bookedDate)

            findNavController().navigateUp()
        }
    }

}