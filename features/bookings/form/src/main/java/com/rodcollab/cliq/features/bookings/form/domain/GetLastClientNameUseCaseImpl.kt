package com.rodcollab.cliq.features.bookings.form.domain

import com.rodcollab.core.data.repository.ClientRepository
import com.rodcollab.cliq.features.bookings.form.model.ClientItem
import javax.inject.Inject

internal class GetLastClientNameUseCaseImpl @Inject constructor(private val clientRepository: ClientRepository) :
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