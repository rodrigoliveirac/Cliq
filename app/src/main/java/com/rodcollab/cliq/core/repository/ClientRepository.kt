package com.rodcollab.cliq.core.repository

import com.rodcollab.cliq.core.model.ClientDomain

interface ClientRepository {

    suspend fun add(
        name: String,
        lastName: String,
        address: String,
        phoneNumber: String,
        birthday: String
    )

    suspend fun fetchClients(): List<ClientDomain>
}