package com.rodcollab.cliq.collections.bookings.domain

import com.rodcollab.cliq.collections.bookings.model.BookingItem
import com.rodcollab.cliq.core.repository.BookingRepository

class GetBookingsUseCaseImpl(private val bookingsRepository: BookingRepository) :
    GetBookingsUseCase {

    override suspend fun invoke(atDate: String): List<BookingItem> {
        return bookingsRepository.fetch(atDate).map {
            BookingItem(
                id = it.id,
                bookedClientName = it.bookedClientName,
                bookedTime = it.bookedTime,
                bookedDate = it.bookedDate
            )
        }
    }
}