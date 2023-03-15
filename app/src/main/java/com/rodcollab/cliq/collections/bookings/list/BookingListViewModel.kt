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
        MutableLiveData<UiState>(
            UiState(
                bookingList = emptyList(),
                currentDate = LocalDate.now().toString(),
                textDate = "TODAY"
            )
        )
    }

    fun pickDate(datePicked: String) {
        viewModelScope.launch {
            uiState.postValue(
                UiState(
                    getBookingUseCase(datePicked),
                    currentDate = datePicked,
                    textDate = textFormatted(datePicked)
                )
            )
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

    private fun refreshBookingList() {
        viewModelScope.launch {
            uiState.postValue(
                UiState(
                    getBookingUseCase(uiState.value?.currentDate.toString()),
                    currentDate = uiState.value?.currentDate.toString(),
                    textDate = uiState.value?.textDate.toString()
                )
            )
        }
    }

    fun onArrowForward() {
        viewModelScope.launch {
            val localDate = LocalDate.parse(uiState.value?.currentDate.toString())
            uiState.postValue(
                UiState(
                    bookingList = getBookingUseCase(
                        localDate.plusDays(1).toString()
                    ),
                    currentDate = localDate.plusDays(1).toString(),
                    textDate = textFormatted(localDate.plusDays(1).toString())
                )
            )
        }
    }

    fun onArrowBack() {
        viewModelScope.launch {
            val localDate = LocalDate.parse(uiState.value?.currentDate.toString())
            uiState.postValue(
                UiState(
                    bookingList = getBookingUseCase(
                        localDate.minusDays(1).toString()
                    ),
                    currentDate = localDate.minusDays(1).toString(),
                    textDate = textFormatted(localDate.minusDays(1).toString())
                )
            )
        }
    }

    data class UiState(val bookingList: List<BookingItem>, val currentDate: String, val textDate: String)

    @Suppress("UNCHECKED_CAST")
    class Factory(private val getBookingUseCase: GetBookingsUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BookingListViewModel(getBookingUseCase) as T
        }
    }
}