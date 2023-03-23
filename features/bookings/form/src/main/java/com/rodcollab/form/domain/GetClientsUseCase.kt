package com.rodcollab.form.domain

import com.rodcollab.form.model.ClientItem

interface GetClientsUseCase {
    suspend operator fun invoke(): List<ClientItem>
}
