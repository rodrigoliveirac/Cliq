package com.rodcollab.cliq.collections.clients.domain

import com.rodcollab.cliq.collections.clients.model.ClientItem

interface GetClientsUseCase {
    suspend operator fun invoke() : List<ClientItem>
}