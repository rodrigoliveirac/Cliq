package com.rodcollab.cliq.collections.bookings.list

import android.content.Context
import androidx.lifecycle.*
import com.rodcollab.cliq.R
import com.rodcollab.cliq.collections.bookings.domain.GetBookingsUseCase
import com.rodcollab.cliq.collections.bookings.model.BookingItem
import com.rodcollab.cliq.core.DateFormat.formatDate
import com.rodcollab.cliq.core.DateFormat.localDateToString
import com.rodcollab.cliq.core.DateFormat.toLocalDate
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

class BookingListViewModel(
    context: Context,
    private val getBookingUseCase: GetBookingsUseCase
) : ViewModel() {

    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData<UiState>(
            UiState(
                bookingList = emptyList(),
                currentDate = todayByDefault(),
                textDate = getStringForToday(context)
            )
        )
    }


    private fun todayByDefault() = now().toString()

    fun pickDate(context: Context, datePicked: Long) {

        viewModelScope.launch {
            uiState.postValue(
                UiState(
                    bookingList = getBookingUseCase(formatDate(datePicked)),
                    currentDate = formatDate(datePicked),
                    textDate = formatText(context, localDateToString(toLocalDate(datePicked)))
                )
            )

        }
    }

    private fun formatText(context: Context, datePicked: String): String {
        return when (datePicked) {
            localDateToString(now()) -> getStringForToday(context)
            localDateToString(nextDayFromNow()) -> getStringForTomorrow(context)
            localDateToString(previousDayFromNow()) -> getStringForYesterday(context)
            else -> datePicked
        }
    }

    private fun getStringForYesterday(context: Context) = context.getString(R.string.yesterday)

    private fun getStringForTomorrow(context: Context) = context.getString(R.string.tomorrow)

    private fun getStringForToday(context: Context) = context.getString(R.string.today)

    private fun previousDayFromNow() = now().minusDays(1)

    private fun nextDayFromNow() = now().plusDays(1)

    private fun now() = LocalDate.now()

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
                    textDate = formatText(context, localDateToString(localDate.plusDays(1)))
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
                    textDate = formatText(
                        context,
                        localDateToString(localDate.minusDays(1))
                    )
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