package com.rodcollab.cliq.core.repository

import com.rodcollab.cliq.core.model.ClientDomain
import java.util.*

object ClientRepositoryImpl : ClientRepository {

    private val clientListCache: MutableList<ClientDomain> = mutableListOf()

    override suspend fun add(name: String, reference: String) {
        clientListCache.add(
            ClientDomain(
                id = UUID.randomUUID().toString(),
                name = name,
                reference = reference
            )
        )
    }

    override suspend fun fetchClients() = clientListCache

}