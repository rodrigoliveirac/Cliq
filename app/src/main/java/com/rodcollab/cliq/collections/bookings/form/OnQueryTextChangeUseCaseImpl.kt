package com.rodcollab.cliq.collections.bookings.form

import com.rodcollab.cliq.collections.clients.domain.GetClientsUseCase
import com.rodcollab.cliq.collections.clients.model.ClientItem

class OnQueryTextChangeUseCaseImpl(
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