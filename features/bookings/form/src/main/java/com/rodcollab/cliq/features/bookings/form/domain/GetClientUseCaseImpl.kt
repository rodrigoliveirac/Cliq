package com.rodcollab.cliq.features.bookings.form.domain

import com.rodcollab.core.data.repository.ClientRepository
import com.rodcollab.cliq.features.bookings.form.model.ClientItem
import javax.inject.Inject

class GetClientsUseCaseImpl @Inject constructor(
    private val clientsRepository: ClientRepository
) : GetClientsUseCase {
    override suspend fun invoke(): List<ClientItem> {
        return clientsRepository.fetchClients().map {
            ClientItem(
                id = it.id,
                name = it.name,
                lastName = it.lastName,
                address = it.address,
            )
        }
    }
}