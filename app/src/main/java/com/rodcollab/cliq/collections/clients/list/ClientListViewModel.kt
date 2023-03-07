package com.rodcollab.cliq.collections.clients.list

import androidx.lifecycle.*
import com.rodcollab.cliq.collections.clients.domain.GetClientsUseCase
import com.rodcollab.cliq.collections.clients.model.ClientItem
import kotlinx.coroutines.launch

class ClientListViewModel(private val getClientsUseCase: GetClientsUseCase) : ViewModel() {

    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData<UiState>(UiState(clientList = emptyList()))
    }

    fun stateOnceAndStream(): LiveData<UiState> {
        return uiState
    }

    fun onResume() {
        viewModelScope.launch {
            refreshClientList()
        }
    }

    private suspend fun refreshClientList() {
        uiState.postValue(UiState(getClientsUseCase()))
    }

    data class UiState(val clientList: List<ClientItem>)

    /**
     * ViewModel Factory needed to provide Repository injection to ViewModel.
     */
    @Suppress("UNCHECKED_CAST")
    class Factory(private val getClientsUseCase: GetClientsUseCase) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ClientListViewModel(getClientsUseCase) as T
        }
    }
}