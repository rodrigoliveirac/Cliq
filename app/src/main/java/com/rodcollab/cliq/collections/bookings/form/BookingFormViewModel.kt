package com.rodcollab.cliq.collections.bookings.form

import androidx.lifecycle.*
import com.rodcollab.cliq.core.DateFormat.formatDate
import com.rodcollab.cliq.core.repository.BookingRepository
import kotlinx.coroutines.launch
import java.time.*
import java.util.*

class BookingFormViewModel(
    private val bookingRepository: BookingRepository
) : ViewModel() {

    fun addBooking(
        bookedClientId: String,
        bookedClientName: String,
        bookedClientAddress: String,
        bookedDate: String,
        time: Long,
    ) {
        viewModelScope.launch {

            bookingRepository.add(
                bookedClientId,
                bookedClientName,
                bookedClientAddress,
                bookedDate,
                time
            )
        }
    }

    private val setValueDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val getValueDateSelected: LiveData<String> = setValueDate

    fun saveValueDate(dateSelected: Long) {
        setValueDate.value = formatDate(dateSelected)
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


