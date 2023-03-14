package com.rodcollab.cliq.core.repository

import com.rodcollab.cliq.core.model.BookingDomain
import java.util.*

object BookingRepositoryImpl : BookingRepository {

    private val bookingListCache: MutableList<BookingDomain> = mutableListOf()

    override suspend fun fetchAll() = bookingListCache
    override suspend fun add(bookedClientId: String, bookedClientName: String, bookedDate: String, bookedTime: Long) {
        bookingListCache.add(
            BookingDomain(
                id = UUID.randomUUID().toString(),
                bookedClientId = bookedClientId,
                bookedClientName = bookedClientName,
                bookedDate = bookedDate,
                bookedTime = bookedTime,
            )
        )
    }

}