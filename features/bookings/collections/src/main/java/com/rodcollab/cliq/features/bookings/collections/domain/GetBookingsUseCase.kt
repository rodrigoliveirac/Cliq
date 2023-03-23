package com.rodcollab.cliq.features.bookings.collections.domain

import com.rodcollab.cliq.features.bookings.collections.model.BookingItem

interface GetBookingsUseCase {
    suspend operator fun invoke(atDate: String): List<BookingItem>
}