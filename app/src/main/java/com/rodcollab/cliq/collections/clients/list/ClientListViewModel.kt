package com.rodcollab.cliq.collections.clients.list

import androidx.lifecycle.*
import com.rodcollab.cliq.collections.clients.domain.GetClientsUseCase
import com.rodcollab.cliq.collections.clients.model.ClientItem
import kotlinx.coroutines.launch

class ClientListViewModel(private val repository: ClientsRepository) : ViewModel() {

    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData<UiState>(UiState(clientList = repository.fetchClients()))
    }

    fun stateOnceAndStream(): LiveData<UiState> {
        return uiState
    }

    fun addClient(
        name: String,
        reference: String
    ) {
        repository.add(name, reference)
        refreshClientList()
    }

    private fun refreshClientList() {
        uiState.value?.let { currentState ->
            uiState.value = currentState.copy(clientList = repository.fetchClients())
        }
    }

    data class UiState(val clientList: List<ClientItem>)

    /**
     * ViewModel Factory needed to provide Repository injection to ViewModel.
     */
    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: ClientsRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ClientListViewModel(repository) as T
        }
    }
}