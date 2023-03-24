package com.rodcollab.cliq.features.bookings.form.domain

interface GetLastClientNameUseCase {
    suspend operator fun invoke(): String
}