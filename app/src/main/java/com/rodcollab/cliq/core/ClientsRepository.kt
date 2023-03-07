package com.rodcollab.cliq.core

import com.rodcollab.cliq.collections.ClientItem

interface ClientsRepository {

    fun add(name: String, reference: String)

    fun fetchClients(): List<ClientItem>

}
