package com.rodcollab.cliq.collections.bookings.domain

import com.rodcollab.cliq.collections.bookings.model.BookingItem
import com.rodcollab.cliq.core.repository.BookingRepository

class GetBookingsUseCaseImpl(private val bookingsRepository: BookingRepository) :
    GetBookingsUseCase {

    override suspend fun invoke(): List<BookingItem> {
        return bookingsRepository.fetchAll().map {
            BookingItem(
                id = it.id,
                clientName = it.bookedClientName,
                bookedTime = it.bookedTime,
                bookedDate = it.bookedDate
            )
        }
    }
}