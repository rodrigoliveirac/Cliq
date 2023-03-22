package com.rodcollab.cliq.collections.bookings.domain

import com.rodcollab.cliq.collections.clients.model.ClientItem
import com.rodcollab.cliq.core.repository.ClientRepository
import javax.inject.Inject

class OnSelectedClientUseCaseImpl @Inject constructor(
    private val clientRepository: ClientRepository
) : OnSelectedClientUseCase {
    override suspend fun invoke(clientId: String): ClientItem {
        val client = clientRepository.fetchClient(clientId)
        return ClientItem(
            id = client.id,
            name = client.name,
            lastName = client.lastName,
            address = client.address,
        )
    }

}