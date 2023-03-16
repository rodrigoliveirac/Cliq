package com.rodcollab.cliq.core.repository

import com.rodcollab.cliq.core.database.AppDatabase
import com.rodcollab.cliq.core.database.ClientDao
import com.rodcollab.cliq.core.database.entity.Client
import com.rodcollab.cliq.core.model.ClientDomain
import java.util.*

class ClientRepositoryImpl(appDatabase: AppDatabase) : ClientRepository {

    private var clientDao: ClientDao = appDatabase.clientDao()

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

        clientDao.insert(client)
    }

    override suspend fun fetchClients() = clientDao.fetchAllClients().map {
        ClientDomain(
            id = it.uuid,
            name = it.name,
            lastName = it.lastName,
            address = it.address,
            phoneNumber = it.phoneNumber,
            birthday = it.birthday
        )
    }

}