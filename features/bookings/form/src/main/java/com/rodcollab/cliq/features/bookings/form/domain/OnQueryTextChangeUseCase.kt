package com.rodcollab.cliq.features.bookings.form.domain

import com.rodcollab.cliq.features.bookings.form.model.ClientItem

interface OnQueryTextChangeUseCase {

    suspend operator fun invoke(query: String): List<ClientItem>
}
