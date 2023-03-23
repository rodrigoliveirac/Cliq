package com.rodcollab.form.domain

import com.rodcollab.form.model.ClientItem
import javax.inject.Inject

class OnSelectedClientUseCaseImpl @Inject constructor(
    private val clientRepository: com.rodcollab.core.data.repository.ClientRepository
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