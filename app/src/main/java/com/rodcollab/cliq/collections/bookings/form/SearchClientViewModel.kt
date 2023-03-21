package com.rodcollab.cliq.collections.bookings.form

import android.util.Log
import androidx.lifecycle.*
import com.rodcollab.cliq.collections.clients.domain.GetClientsUseCase
import com.rodcollab.cliq.collections.clients.model.ClientItem
import kotlinx.coroutines.launch

class SearchClientViewModel(
    private val getClientsUseCase: GetClientsUseCase,
    private val onQueryTextChangeUseCase: OnQueryTextChangeUseCase,
) : ViewModel() {

    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData<UiState>(UiState(clientList = emptyList()))
    }

    private val clientSelectedState: MutableLiveData<ClientSelectedState> by lazy {
        MutableLiveData<ClientSelectedState>()
    }

    private val lastClient: MutableLiveData<LastClient> by lazy {
        MutableLiveData<LastClient>(LastClient(name = ""))
    }

    data class LastClient(val name: String)

    fun getLastClient() : LiveData<LastClient>  {
        viewModelScope.launch {
            lastClient.value?.let {
                lastClient.value = if(getClientsUseCase().isEmpty()) {
                    it.copy(name = "")
                } else {
                    it.copy(name = getClientsUseCase().last().name)
                }
            }

        }
        return lastClient
    }

    data class ClientSelectedState(
        val wasSelected: Boolean,
        val clientSelected: ClientItem?
    )

    fun clientSelected(): LiveData<ClientSelectedState> {
        return clientSelectedState
    }

    fun stateOnceAndStream(): LiveData<UiState> {
        return uiState
    }

    fun onQueryTextChange(query: String) {
        viewModelScope.launch {
                uiState.postValue(UiState(onQueryTextChangeUseCase(query)))
        }
    }

    fun onItemClicked(id: String) {
        viewModelScope.launch {
            val clientSelected = getClientsUseCase().first {
                it.id == id
            }
            clientSelectedState.postValue(ClientSelectedState(true, clientSelected))
            Log.d("clientIdViewModelSearch", id)
        }
    }

    fun resetClientSelected() {
        viewModelScope.launch {
            clientSelectedState.postValue(ClientSelectedState(false, null))
        }
    }

    data class UiState(val clientList: List<ClientItem>)


    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val getClientsUseCase: GetClientsUseCase,
        private val onQueryTextChangeUseCase: OnQueryTextChangeUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchClientViewModel(getClientsUseCase, onQueryTextChangeUseCase) as T
        }
    }
}
