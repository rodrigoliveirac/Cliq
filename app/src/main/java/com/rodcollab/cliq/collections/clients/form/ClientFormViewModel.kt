package com.rodcollab.cliq.collections.clients.form

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
        reference: String
    ) {
        viewModelScope.launch {
            clientRepository.add(
                name, reference
            )
        }
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