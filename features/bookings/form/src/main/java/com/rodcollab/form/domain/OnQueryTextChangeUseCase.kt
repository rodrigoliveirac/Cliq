package com.rodcollab.form.domain

import com.rodcollab.form.model.ClientItem

interface OnQueryTextChangeUseCase {

    suspend operator fun invoke(query: String): List<ClientItem>
}
