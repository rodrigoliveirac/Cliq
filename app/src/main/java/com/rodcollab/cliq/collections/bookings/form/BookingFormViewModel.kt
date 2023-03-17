package com.rodcollab.cliq.collections.bookings.form

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.rodcollab.cliq.core.repository.BookingRepository
import kotlinx.coroutines.launch
import java.time.*
import java.time.format.DateTimeFormatter
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

    val getValueDateSelected: LiveData<String> = setValueDate


    @RequiresApi(Build.VERSION_CODES.O)
    fun saveValueDate(dateSelected: Long) {
        setValueDate.value = formattedDate(dateSelected)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formattedDate(dateSelected: Long): String {

        val date = getDate(dateSelected)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault())

        val selectedDate = LocalDate.parse(formatter.format(date.toInstant()).toString(), formatter).plusDays(1).toString()
        Log.d("datePicked", selectedDate)
        return selectedDate
    }

    private fun getDate(dateSelected: Long) : Date {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateSelected
        return calendar.time
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


