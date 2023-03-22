package com.rodcollab.cliq.collections.bookings.domain

import com.rodcollab.cliq.collections.clients.model.ClientItem
import com.rodcollab.cliq.core.repository.ClientRepository
import javax.inject.Inject

class GetLastClientNameUseCaseImpl @Inject constructor(private val clientRepository: ClientRepository) :
    GetLastClientNameUseCase {
    override suspend fun invoke(): String {
        val clients = clientRepository.fetchClients().map {
            ClientItem(
                id = it.id,
                name = it.name,
                lastName = it.lastName,
                address = it.address
            )
        }
        return if (clients.isEmpty()) {
            ""
        } else {
            clients.last().name
        }
    }

}