package com.rodcollab.cliq.collections.bookings.form

import androidx.lifecycle.*
import com.rodcollab.cliq.collections.clients.model.ClientItem
import com.rodcollab.cliq.core.repository.BookingRepository
import kotlinx.coroutines.launch

class BookingFormViewModel(
    private val onQueryTextChangeUseCase: OnQueryTextChangeUseCase,
    private val bookingRepository: BookingRepository
) : ViewModel() {

    private val _clientSelected: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val clientSelected: LiveData<Boolean> = _clientSelected


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
        time: Long,
    ) {
        bookingRepository.add(clientName, bookedDate, time)
    }

    private val name: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val liveName: LiveData<String> = name

    fun bookingSaved(bookingSaved: Boolean) {
        _clientSelected.value.let {
            _clientSelected.value = bookingSaved
        }
    }

    fun onItemClicked(nameSelected: String) {
        name.value?.let {
            name.value = nameSelected
        }
        _clientSelected.value?.let {
            _clientSelected.value = true
        }
    }

    private val setValueDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val getValueDate: LiveData<String> = setValueDate

    fun saveValueDate(headerText: String?) {
        setValueDate.value = headerText
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val onQueryTextChangeUseCase: OnQueryTextChangeUseCase,
        private val bookingRepository: BookingRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BookingFormViewModel(onQueryTextChangeUseCase, bookingRepository) as T
        }
    }
}


