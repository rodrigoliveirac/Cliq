package com.rodcollab.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodcollab.core.data.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientFormViewModel @Inject constructor(
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

}