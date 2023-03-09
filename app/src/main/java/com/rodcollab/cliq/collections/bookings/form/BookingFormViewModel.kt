package com.rodcollab.cliq.collections.bookings.form

import androidx.lifecycle.*
import com.rodcollab.cliq.collections.clients.model.ClientItem
import com.rodcollab.cliq.core.repository.BookingRepository
import kotlinx.coroutines.launch

class BookingFormViewModel(
    private val onQueryTextChangeUseCase : OnQueryTextChangeUseCase,
    private val bookingRepository: BookingRepository
) : ViewModel() {

    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData<UiState>(UiState(clientList = emptyList()))
    }

    fun stateOnceAndStream(): LiveData<UiState> {
        return uiState
    }

    fun onQueryTextChange(query: String) {
        viewModelScope.launch {
            uiState.postValue(UiState(onQueryTextChangeUseCase(query)))
        }
    }

    data class UiState(val clientList: List<ClientItem>)

    fun addBooking(
        clientName: String,
        bookedDate: String,
        time: String,
    ) {
        bookingRepository.add(clientName, bookedDate, time)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val onQueryTextChangeUseCase: OnQueryTextChangeUseCase,
        private val bookingRepository: BookingRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BookingFormViewModel(onQueryTextChangeUseCase,bookingRepository) as T
        }
    }
}


