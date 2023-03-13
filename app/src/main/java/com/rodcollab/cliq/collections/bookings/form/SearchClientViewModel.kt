package com.rodcollab.cliq.collections.bookings.form

import androidx.lifecycle.*
import com.rodcollab.cliq.collections.clients.model.ClientItem
import kotlinx.coroutines.launch

class SearchClientViewModel(
    private val onQueryTextChangeUseCase: OnQueryTextChangeUseCase,
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


    @Suppress("UNCHECKED_CAST")
    class Factory(private val onQueryTextChangeUseCase: OnQueryTextChangeUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchClientViewModel(onQueryTextChangeUseCase) as T
        }
    }
}
