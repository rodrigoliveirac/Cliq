package com.rodcollab.form.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodcollab.cliq.DateFormat.formatDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingFormViewModel @Inject constructor(
    private val bookingRepository: com.rodcollab.core.data.repository.BookingRepository,
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
}


