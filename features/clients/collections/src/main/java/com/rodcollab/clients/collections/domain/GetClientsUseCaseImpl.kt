package com.rodcollab.clients.collections.domain

import com.rodcollab.clients.collections.model.ClientItem
import com.rodcollab.core.data.repository.ClientRepository
import javax.inject.Inject

internal class GetClientsUseCaseImpl @Inject constructor(
    private val clientRepository: ClientRepository
) : GetClientsUseCase {
    override suspend fun invoke(): List<ClientItem> {
        return clientRepository.fetchClients().map {
            ClientItem(
                id = it.id,
                name = it.name,
                lastName = it.lastName,
                address = it.address
            )
        }
    }
}