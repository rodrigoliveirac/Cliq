package com.rodcollab.cliq.collections.bookings.domain

import com.rodcollab.cliq.collections.clients.model.ClientItem

interface OnSelectedClientUseCase {

    suspend operator fun invoke(clientId: String): ClientItem
}