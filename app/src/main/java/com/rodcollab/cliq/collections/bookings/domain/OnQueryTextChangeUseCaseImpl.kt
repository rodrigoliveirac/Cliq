package com.rodcollab.cliq.collections.bookings.domain

import com.rodcollab.cliq.collections.clients.domain.GetClientsUseCase
import com.rodcollab.cliq.collections.clients.model.ClientItem
import javax.inject.Inject

class OnQueryTextChangeUseCaseImpl @Inject constructor(
    private val getClientsUseCase: GetClientsUseCase,
) : OnQueryTextChangeUseCase {

    override suspend fun invoke(query: String): List<ClientItem> {
        return if (query.isNotBlank() && query.isNotEmpty()) {
            getClientsUseCase().filter { it.name.startsWith(query, ignoreCase = true) }
        } else {
            emptyList()
        }
    }

}