package com.rodcollab.cliq.core.repository

import com.rodcollab.cliq.core.model.ClientDomain
import java.util.*

object ClientRepositoryImpl : ClientRepository {

    private val clientListCache: MutableList<ClientDomain> = mutableListOf(
        ClientDomain(
            id = UUID.randomUUID().toString(),
            name = "rodrigo",
            lastName = "lastName",
            address = "address",
            phoneNumber = "phoneNumber",
            birthday = "birthday"
        ),
        ClientDomain(
            id = UUID.randomUUID().toString(),
            name = "jeni",
            lastName = "lastName",
            address = "address",
            phoneNumber = "phoneNumber",
            birthday = "birthday"
        ),
        ClientDomain(
            id = UUID.randomUUID().toString(),
            name = "roxy",
            lastName = "lastName",
            address = "address",
            phoneNumber = "phoneNumber",
            birthday = "birthday"
        ),
        ClientDomain(
            id = UUID.randomUUID().toString(),
            name = "francisco",
            lastName = "lastName",
            address = "address",
            phoneNumber = "phoneNumber",
            birthday = "birthday"
        ),

    )

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