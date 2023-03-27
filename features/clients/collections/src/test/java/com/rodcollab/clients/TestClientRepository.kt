package com.rodcollab.clients

import com.rodcollab.core.data.model.ClientDomain
import com.rodcollab.core.data.repository.ClientRepository
import java.util.*

class TestClientRepository : ClientRepository {

    var clientList = mutableListOf<ClientDomain>()

    override suspend fun add(
        name: String,
        lastName: String,
        address: String,
        phoneNumber: String,
        birthday: String
    ) {
        clientList.add(
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

    override suspend fun fetchClients() = clientList.map {
        ClientDomain(
            id = it.id,
            name = it.name,
            lastName = it.lastName,
            address = it.address,
            phoneNumber = it.phoneNumber,
            birthday = it.birthday
        )
    }

    override suspend fun fetchClient(clientId: String): ClientDomain {
        return clientList.first { it.id == clientId }
    }
}