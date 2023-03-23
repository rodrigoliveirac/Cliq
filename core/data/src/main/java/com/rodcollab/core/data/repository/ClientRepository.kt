package com.rodcollab.core.data.repository

import com.rodcollab.core.data.model.ClientDomain

interface ClientRepository {

    suspend fun add(
        name: String,
        lastName: String,
        address: String,
        phoneNumber: String,
        birthday: String
    )

    suspend fun fetchClients(): List<ClientDomain>

    suspend fun fetchClient(clientId: String) : ClientDomain
}