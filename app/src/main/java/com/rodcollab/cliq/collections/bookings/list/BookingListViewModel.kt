package com.rodcollab.cliq.collections.bookings.list

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.rodcollab.cliq.R
import com.rodcollab.cliq.collections.bookings.domain.GetBookingsUseCase
import com.rodcollab.cliq.collections.bookings.model.BookingItem
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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
                currentDate = todayByDefault(),
                textDate = getStringForToday(context)
            )
        )
    }



    private fun todayByDefault() = now().toString()

    fun pickDate(context: Context, datePicked: Long) {

        val date = getDate(datePicked)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault())
        val selectedDate = LocalDate.parse(formatter.format(date.toInstant()).toString(), formatter).plusDays(1)

        viewModelScope.launch {
            uiState.postValue(
                UiState(
                    bookingList = getBookingUseCase(formatDate(datePicked)),
                    currentDate = formatDate(datePicked),
                    textDate = formatText(context, localDateToString(selectedDate))
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

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(dateSelected: Long): String {
        val date = getDate(dateSelected)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault())
        return LocalDate.parse(formatter.format(date.toInstant()).toString(), formatter).plusDays(1)
            .toString()
    }

    private fun localDateToString(localDate: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy").withZone(ZoneId.systemDefault())
        return formatter.format(localDate)
    }

    private fun getDate(dateSelected: Long): Date {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateSelected
        return calendar.time
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