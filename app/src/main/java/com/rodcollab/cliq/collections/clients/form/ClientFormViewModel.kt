package com.rodcollab.cliq.collections.clients.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rodcollab.cliq.core.repository.ClientRepository
import kotlinx.coroutines.launch

class ClientFormViewModel(
    private val clientRepository: ClientRepository
) : ViewModel() {

    fun addClient(
        name: String,
        lastName: String,
        address: String,
        phoneNumber: String,
        birthday: String
    ) {
        viewModelScope.launch {
            clientRepository.add(
                name, lastName, address, phoneNumber, birthday
            )
        }
    }

    private val _getValueDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val getValueDate: LiveData<String> = _getValueDate

    fun saveValueDate(headerText: String?) {
        _getValueDate.value = headerText
    }

    /**
     * ViewModel Factory needed to provide Repository injection to ViewModel.
     */
    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: ClientRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ClientFormViewModel(repository) as T
        }
    }
}