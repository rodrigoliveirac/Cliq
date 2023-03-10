package com.rodcollab.cliq.collections.clients.domain

import com.rodcollab.cliq.collections.clients.model.ClientItem
import com.rodcollab.cliq.core.repository.ClientRepository

class GetClientsUseCaseImpl(
    private val clientRepository: ClientRepository
) : GetClientsUseCase {
    override suspend fun invoke(): List<ClientItem> {
        return clientRepository.fetchClients().map {
            ClientItem(
                id = it.id,
                name = it.name,
                reference = it.reference
            )
        }
    }
}