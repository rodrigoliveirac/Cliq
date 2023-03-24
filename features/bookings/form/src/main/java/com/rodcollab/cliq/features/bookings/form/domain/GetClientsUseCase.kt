package com.rodcollab.cliq.features.bookings.form.domain

import com.rodcollab.cliq.features.bookings.form.model.ClientItem

interface GetClientsUseCase {
    suspend operator fun invoke(): List<ClientItem>
}
