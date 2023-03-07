package com.rodcollab.cliq.dummy

import com.rodcollab.cliq.collections.ClientItem
import com.rodcollab.cliq.core.ClientsRepository
import java.util.*

object MockClients : ClientsRepository {

    private var clientsList: MutableList<ClientItem> = mutableListOf()

    override fun add(name: String, reference: String) {
        clientsList.add(
            ClientItem(
                id = UUID.randomUUID().toString(),
                name = name,
                reference = reference
            )
        )
    }

    override fun fetchClients(): List<ClientItem> {
        return clientsList.map { it.copy() }
    }


}