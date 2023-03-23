package com.rodcollab.clients.collections.ui

import androidx.lifecycle.*
import com.rodcollab.clients.collections.domain.GetClientsUseCase
import com.rodcollab.clients.collections.model.ClientItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientListViewModel @Inject constructor(private val getClientsUseCase: GetClientsUseCase) : ViewModel() {

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

}