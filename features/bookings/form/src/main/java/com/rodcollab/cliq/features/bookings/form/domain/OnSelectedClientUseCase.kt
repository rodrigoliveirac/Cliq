package com.rodcollab.cliq.features.bookings.form.domain

import com.rodcollab.cliq.features.bookings.form.model.ClientItem


interface OnSelectedClientUseCase {

    suspend operator fun invoke(clientId: String): ClientItem
}