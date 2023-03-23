package com.rodcollab.clients.collections.domain

import com.rodcollab.clients.collections.model.ClientItem

interface GetClientsUseCase {
    suspend operator fun invoke() : List<ClientItem>
}