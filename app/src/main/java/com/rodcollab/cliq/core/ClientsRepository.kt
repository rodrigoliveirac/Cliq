package com.rodcollab.cliq.core

import com.rodcollab.cliq.ClientItem

interface ClientsRepository {

    fun add()

    fun fetchClients(): List<ClientItem>

}
