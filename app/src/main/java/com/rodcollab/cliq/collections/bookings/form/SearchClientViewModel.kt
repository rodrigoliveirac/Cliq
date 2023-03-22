package com.rodcollab.cliq.collections.bookings.form

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodcollab.cliq.collections.bookings.domain.GetLastClientNameUseCase
import com.rodcollab.cliq.collections.bookings.domain.OnQueryTextChangeUseCase
import com.rodcollab.cliq.collections.bookings.domain.OnSelectedClientUseCase
import com.rodcollab.cliq.collections.clients.model.ClientItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchClientViewModel @Inject constructor(
    private val getOnSelectedClientUseCase: OnSelectedClientUseCase,
    private val getLastClientNameUseCase: GetLastClientNameUseCase,
    private val onQueryTextChangeUseCase: OnQueryTextChangeUseCase,
) : ViewModel() {

    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData<UiState>(UiState(clientList = emptyList()))
    }

    private val clientSelectedState: MutableLiveData<ClientSelectedState> by lazy {
        MutableLiveData<ClientSelectedState>()
    }

    private val lastClient: MutableLiveData<LastClient> by lazy {
        MutableLiveData<LastClient>()
    }

    data class LastClient(val name: String)

    fun getLastClient(): LiveData<LastClient> {

        viewModelScope.launch {
            lastClient.postValue(LastClient(getLastClientNameUseCase()))
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

            val clientSelected = getOnSelectedClientUseCase(id)

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
}
