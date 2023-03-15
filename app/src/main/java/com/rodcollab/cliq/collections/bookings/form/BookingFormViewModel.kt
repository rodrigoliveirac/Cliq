package com.rodcollab.cliq.collections.bookings.form

import android.util.Log
import androidx.lifecycle.*
import com.rodcollab.cliq.core.repository.BookingRepository
import kotlinx.coroutines.launch
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
            Log.d("clientIdViewModelFormAfterAdd", bookedClientId)
        }
    }

    private val setValueDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val getValueDate: LiveData<String> = setValueDate

    fun saveValueDate(headerText: String?) {
        setValueDate.value = headerText

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


