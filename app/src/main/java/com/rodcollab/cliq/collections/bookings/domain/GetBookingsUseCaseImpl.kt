package com.rodcollab.cliq.collections.bookings.domain

import com.rodcollab.cliq.collections.bookings.model.BookingItem
import com.rodcollab.cliq.core.repository.BookingRepository
import javax.inject.Inject

class GetBookingsUseCaseImpl @Inject constructor(
    private val bookingsRepository: BookingRepository
) :
    GetBookingsUseCase {

    override suspend fun invoke(atDate: String): List<BookingItem> {
        return bookingsRepository.fetchByDate(atDate).map { bookedClient ->
            BookingItem(
                id = bookedClient.id,
                bookedClientName = bookedClient.bookedClientName,
                bookedClientAddress = bookedClient.bookedClientAddress,
                bookedTime = bookedClient.bookedTime,
                bookedDate = bookedClient.bookedDate
            )
        }
    }
}