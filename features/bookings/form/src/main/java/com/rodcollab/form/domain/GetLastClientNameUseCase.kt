package com.rodcollab.form.domain

interface GetLastClientNameUseCase {
    suspend operator fun invoke(): String
}