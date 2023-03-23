package com.rodcollab.core.data.repository

import com.rodcollab.core.data.database.ClientDao
import com.rodcollab.core.data.database.entity.Client
import com.rodcollab.core.data.model.ClientDomain
import java.util.*
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(private val dao: ClientDao) : ClientRepository {

    override suspend fun add(
        name: String,
        lastName: String,
        address: String,
        phoneNumber: String,
        birthday: String
    ) {
        val client = Client(
            uuid = UUID.randomUUID().toString(),
            name = name,
            lastName = lastName,
            address = address,
            phoneNumber = phoneNumber,
            birthday = birthday
        )

        dao.insert(client)
    }

    override suspend fun fetchClients() = dao.fetchAllClients().map {
        ClientDomain(
            id = it.uuid,
            name = it.name,
            lastName = it.lastName,
            address = it.address,
            phoneNumber = it.phoneNumber,
            birthday = it.birthday
        )
    }

    override suspend fun fetchClient(clientId: String): ClientDomain {
        val client = dao.fetchClient(clientId)
        return ClientDomain(
            id = client.uuid,
            name = client.name,
            lastName = client.lastName,
            address = client.address,
            phoneNumber = client.phoneNumber,
            birthday = client.birthday
        )
    }

}