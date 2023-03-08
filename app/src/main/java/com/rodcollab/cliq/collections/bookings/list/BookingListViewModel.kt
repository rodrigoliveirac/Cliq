package com.rodcollab.cliq.collections.bookings.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rodcollab.cliq.collections.bookings.domain.GetBookingsUseCase
import com.rodcollab.cliq.collections.bookings.model.BookingItem
import kotlinx.coroutines.launch

class BookingListViewModel(
    private val getBookingUseCase: GetBookingsUseCase
) : ViewModel() {

    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData<UiState>(UiState(emptyList()))
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
        uiState.postValue(UiState(getBookingUseCase()))
    }

    data class UiState(val bookingList: List<BookingItem>)

    @Suppress("UNCHECKED_CAST")
    class Factory(private val getBookingUseCase: GetBookingsUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BookingListViewModel(getBookingUseCase) as T
        }
    }
}