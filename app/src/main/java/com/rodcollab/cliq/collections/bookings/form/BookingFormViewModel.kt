package com.rodcollab.cliq.collections.bookings.form

import androidx.lifecycle.*
import com.rodcollab.cliq.core.repository.BookingRepository


class BookingFormViewModel(
    private val bookingRepository: BookingRepository
) : ViewModel() {



    fun addBooking(
        time: String,
        clientName: String,
        bookedDate: String
    ) {
        bookingRepository.add(time, clientName, bookedDate)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val bookingRepository: BookingRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BookingFormViewModel(bookingRepository) as T
        }
    }
}


