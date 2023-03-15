package com.rodcollab.cliq.core.repository

import com.rodcollab.cliq.core.model.ClientDomain
import java.util.*

object ClientRepositoryImpl : ClientRepository {

    private val clientListCache: MutableList<ClientDomain> = mutableListOf()

    override suspend fun add(
        name: String,
        lastName: String,
        address: String,
        phoneNumber: String,
        birthday: String
    ) {
        clientListCache.add(
            ClientDomain(
                id = UUID.randomUUID().toString(),
                name = name,
                lastName = lastName,
                address = address,
                phoneNumber = phoneNumber,
                birthday = birthday
            )
        )
    }

    override suspend fun fetchClients() = clientListCache

}