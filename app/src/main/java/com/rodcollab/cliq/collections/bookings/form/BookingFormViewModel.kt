package com.rodcollab.cliq.collections.bookings.form

import androidx.lifecycle.*
import com.rodcollab.cliq.core.repository.BookingRepository
import kotlinx.coroutines.launch

class BookingFormViewModel(
    private val bookingRepository: BookingRepository
) : ViewModel() {

    fun addBooking(
        clientName: String,
        bookedDate: String,
        time: Long,
    ) {
        viewModelScope.launch {
            bookingRepository.add(clientName, bookedDate, time)
        }
    }

    private val name: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val liveName: LiveData<String> = name

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


