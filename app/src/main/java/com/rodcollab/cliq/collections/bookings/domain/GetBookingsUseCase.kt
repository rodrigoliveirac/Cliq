package com.rodcollab.cliq.collections.bookings.domain

import com.rodcollab.cliq.collections.bookings.model.BookingItem

interface GetBookingsUseCase {
    suspend operator fun invoke(): List<BookingItem>
}