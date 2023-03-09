package com.rodcollab.cliq.core.repository

import com.rodcollab.cliq.core.model.BookingDomain
import java.util.*

object BookingRepositoryImpl : BookingRepository {

    private val bookingListCache: MutableList<BookingDomain> = mutableListOf()

    override fun fetchAll() = bookingListCache
    override fun add(bookedClientName: String, bookedDate: String, bookedTime: String) {
        bookingListCache.add(
            BookingDomain(
                id = UUID.randomUUID().toString(),
                bookedClientName = bookedClientName,
                bookedDate = bookedDate,
                bookedTime = 10000,
            )
        )
    }

}