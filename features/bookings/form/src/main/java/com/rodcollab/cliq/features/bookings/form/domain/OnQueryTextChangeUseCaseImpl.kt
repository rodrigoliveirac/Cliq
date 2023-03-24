package com.rodcollab.cliq.features.bookings.form.domain

import com.rodcollab.cliq.features.bookings.form.model.ClientItem
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