package com.rodcollab.cliq.core.repository

import com.rodcollab.cliq.core.model.ClientDomain

interface ClientRepository {

    suspend fun add(name: String, reference: String)

    suspend fun fetchClients(): List<ClientDomain>
}