package com.rodcollab.cliq.collections.bookings.list

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.rodcollab.cliq.R
import com.rodcollab.cliq.collections.bookings.domain.GetBookingsUseCase
import com.rodcollab.cliq.collections.bookings.model.BookingItem
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
class BookingListViewModel(
    context: Context,
    private val getBookingUseCase: GetBookingsUseCase
) : ViewModel() {

    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData<UiState>(
            UiState(
                bookingList = emptyList(),
                currentDate = LocalDate.now().toString(),
                textDate = context.getString(R.string.today)
            )
        )
    }

    fun pickDate(context: Context, datePicked: String) {
        viewModelScope.launch {
            uiState.postValue(
                UiState(
                    getBookingUseCase(datePicked),
                    currentDate = datePicked,
                    textDate = textFormatted(context, datePicked)
                )
            )
        }
    }

    private fun textFormatted(context: Context, datePicked: String): String {
        return when (datePicked) {
            LocalDate.now().toString() -> context.getString(R.string.today)
            LocalDate.now().plusDays(1).toString() -> context.getString(R.string.tomorrow)
            LocalDate.now().minusDays(1).toString() -> context.getString(R.string.yesterday)
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

    fun onArrowForward(context: Context) {
        viewModelScope.launch {
            val localDate = LocalDate.parse(uiState.value?.currentDate.toString())
            uiState.postValue(
                UiState(
                    bookingList = getBookingUseCase(
                        localDate.plusDays(1).toString()
                    ),
                    currentDate = localDate.plusDays(1).toString(),
                    textDate = textFormatted(context, localDate.plusDays(1).toString())
                )
            )
        }
    }

    fun onArrowBack(context: Context) {
        viewModelScope.launch {
            val localDate = LocalDate.parse(uiState.value?.currentDate.toString())
            uiState.postValue(
                UiState(
                    bookingList = getBookingUseCase(
                        localDate.minusDays(1).toString()
                    ),
                    currentDate = localDate.minusDays(1).toString(),
                    textDate = textFormatted(context, localDate.minusDays(1).toString())
                )
            )
        }
    }

    data class UiState(
        val bookingList: List<BookingItem>,
        val currentDate: String,
        val textDate: String
    )

    @Suppress("UNCHECKED_CAST")
    class Factory(private val context: Context, private val getBookingUseCase: GetBookingsUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BookingListViewModel(context, getBookingUseCase) as T
        }
    }
}