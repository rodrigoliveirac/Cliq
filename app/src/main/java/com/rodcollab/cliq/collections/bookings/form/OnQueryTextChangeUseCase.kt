package com.rodcollab.cliq.collections.bookings.form

import com.rodcollab.cliq.collections.clients.model.ClientItem

interface OnQueryTextChangeUseCase {

    suspend operator fun invoke(query: String): List<ClientItem>
}
