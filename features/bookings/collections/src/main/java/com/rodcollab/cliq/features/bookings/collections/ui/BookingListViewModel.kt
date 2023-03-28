package com.rodcollab.cliq.features.bookings.collections.ui

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodcollab.cliq.DateFormat.formatDate
import com.rodcollab.cliq.DateFormat.localDateToString
import com.rodcollab.cliq.DateFormat.toLocalDate
import com.rodcollab.cliq.features.bookings.collections.domain.GetBookingsUseCase
import com.rodcollab.cliq.features.bookings.collections.model.BookingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class BookingListViewModel @Inject constructor(
    private val getBookingUseCase: GetBookingsUseCase
) : ViewModel() {

    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData<UiState>(
            UiState(
                bookingList = emptyList(),
            )
        )
    }

    private val dateUiState: MutableLiveData<DateUiState> by lazy {
        MutableLiveData<DateUiState>(
            DateUiState(
                currentDate = now().toString(),
                textDate = todayByDefault()
            )
        )
    }

    private fun todayByDefault() = formatText(app.baseContext, localDateToString(now()))


    fun pickDate(datePicked: Long) {

        viewModelScope.launch {
            uiState.postValue(
                UiState(
                    bookingList = getBookingUseCase(formatDate(datePicked)),
                )
            )
            dateUiState.postValue(
                DateUiState(
                    currentDate = formatDate(datePicked),
                    textDate = formatText(app.baseContext, localDateToString(toLocalDate(datePicked)))
                )
            )

        }
    }

    fun stateOnceAndStream(): LiveData<UiState> {
        return uiState
    }

    fun dateStateOnceAndStream(): LiveData<DateUiState> {
        return dateUiState
    }

    fun onResume() {
        viewModelScope.launch {
            refreshBookingList()
        }
    }

    private suspend fun refreshBookingList() {

        uiState.postValue(
            UiState(
                getBookingUseCase(dateUiState.value?.currentDate.toString())
            )
        )
        dateUiState.postValue(
            DateUiState(
                currentDate = dateUiState.value?.currentDate.toString(),
                textDate = formatText(app.baseContext, dateUiState.value?.textDate.toString())
            )
        )

    }

    fun onArrowForward() {
        viewModelScope.launch {
            val localDate = LocalDate.parse(dateUiState.value?.currentDate.toString())
            uiState.postValue(
                UiState(
                    bookingList = getBookingUseCase(
                        localDate.plusDays(1).toString()
                    ),

                    )
            )
            dateUiState.postValue(
                DateUiState(
                    currentDate = localDate.plusDays(1).toString(),
                    textDate = formatText(app.baseContext, localDateToString(localDate.plusDays(1)))
                )
            )
        }
    }

    fun onArrowBack() {
        viewModelScope.launch {
            val localDate = LocalDate.parse(dateUiState.value?.currentDate.toString())
            uiState.postValue(
                UiState(
                    bookingList = getBookingUseCase(
                        localDate.minusDays(1).toString()
                    ),
                )
            )
            dateUiState.postValue(
                DateUiState(
                    currentDate = localDate.minusDays(1).toString(),
                    textDate = formatText(app.baseContext, localDateToString(localDate.minusDays(1)))
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

    data class UiState(
        val bookingList: List<BookingItem>,
    )

    data class DateUiState(
        val currentDate: String,
        val textDate: String
    )
}