package com.rodcollab.cliq.core

import com.rodcollab.cliq.collections.ClientItem

interface ClientsRepository {

    fun add()

    fun fetchClients(): List<ClientItem>

}
