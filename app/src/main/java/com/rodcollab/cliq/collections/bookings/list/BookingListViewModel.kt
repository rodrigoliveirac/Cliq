package com.rodcollab.cliq.collections.bookings.list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.rodcollab.cliq.collections.bookings.domain.GetBookingsUseCase
import com.rodcollab.cliq.collections.bookings.model.BookingItem
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
class BookingListViewModel(
    private val getBookingUseCase: GetBookingsUseCase
) : ViewModel() {

    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData<UiState>(UiState(emptyList()))
    }

    private val atDate: MutableLiveData<DateState> by lazy {
        MutableLiveData<DateState>(DateState(atDate = LocalDate.now().toString(), "TODAY"))
    }

    val datePicked: LiveData<DateState> = atDate

    data class DateState(
        val atDate: String,
        val textDate: String
    )

    fun pickDate(datePicked: String) {
        viewModelScope.launch {
            atDate.value?.let {
                atDate.value = it.copy(atDate = datePicked, textDate = textFormatted(datePicked))
            }
            uiState.postValue(UiState(getBookingUseCase(datePicked)))
        }
    }

    private fun textFormatted(datePicked: String): String {
        return when (datePicked) {
            LocalDate.now().toString() -> "TODAY"
            LocalDate.now().plusDays(1).toString() -> "TOMORROW"
            LocalDate.now().minusDays(1).toString() -> "YESTERDAY"
            else -> datePicked
        }
    }

    fun stateOnceAndStream(): LiveData<UiState> {
        return uiState
    }

    fun onResume() {
        viewModelScope.launch {
            refreshBookingList()
        }

    }

    private suspend fun refreshBookingList() {
        uiState.postValue(UiState(getBookingUseCase(atDate.value?.atDate.toString())))
    }

    fun onArrowForward() {
        viewModelScope.launch {
            val localDate = LocalDate.parse(atDate.value?.atDate.toString())
            atDate.value?.let {
                atDate.value = it.copy(
                    atDate = localDate.plusDays(1).toString(),
                    textDate = textFormatted(localDate.plusDays(1).toString())
                )
            }
        }
    }

    fun onArrowBack() {
        viewModelScope.launch {
            val localDate = LocalDate.parse(atDate.value?.atDate.toString())
            atDate.value?.let {
                atDate.value = it.copy(
                    atDate = localDate.minusDays(1).toString(),
                    textDate = textFormatted(localDate.minusDays(1).toString())
                )
            }
        }
    }

    data class UiState(val bookingList: List<BookingItem>)

    @Suppress("UNCHECKED_CAST")
    class Factory(private val getBookingUseCase: GetBookingsUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BookingListViewModel(getBookingUseCase) as T
        }
    }
}