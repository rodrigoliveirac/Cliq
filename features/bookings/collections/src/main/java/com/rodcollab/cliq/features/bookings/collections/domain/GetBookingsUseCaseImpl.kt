package com.rodcollab.cliq.features.bookings.collections.domain

import com.rodcollab.cliq.features.bookings.collections.model.BookingItem
import javax.inject.Inject

internal class GetBookingsUseCaseImpl @Inject constructor(
    private val bookingsRepository: com.rodcollab.core.data.repository.BookingRepository
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