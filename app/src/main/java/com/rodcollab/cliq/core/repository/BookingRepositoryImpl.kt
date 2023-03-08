package com.rodcollab.cliq.core.repository

import com.rodcollab.cliq.core.model.BookingDomain
import java.util.*

object BookingRepositoryImpl : BookingRepository {

    private val bookingListCache: MutableList<BookingDomain> = mutableListOf()

    override fun fetchAll() = bookingListCache
    override fun add(bookedTime: String, clientName: String, bookedDate: String) {
        bookingListCache.add(
            BookingDomain(
                id = UUID.randomUUID().toString(),
                bookedTime = 100000,
                bookedClientName = clientName,
                bookedDate = bookedDate
            )
        )
    }

}