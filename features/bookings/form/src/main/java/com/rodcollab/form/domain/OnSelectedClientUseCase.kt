package com.rodcollab.form.domain

import com.rodcollab.form.model.ClientItem


interface OnSelectedClientUseCase {

    suspend operator fun invoke(clientId: String): ClientItem
}