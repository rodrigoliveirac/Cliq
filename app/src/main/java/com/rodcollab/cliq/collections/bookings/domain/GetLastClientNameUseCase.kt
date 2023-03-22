package com.rodcollab.cliq.collections.bookings.domain

interface GetLastClientNameUseCase {
    suspend operator fun invoke(): String
}