package com.rodcollab.cliq.core.repository

import com.rodcollab.cliq.core.database.AppDatabase
import com.rodcollab.cliq.core.database.entity.Booking
import com.rodcollab.cliq.core.model.BookingDomain
import java.util.*

class BookingRepositoryImpl(appDatabase: AppDatabase) : BookingRepository {

    private val bookingDao = appDatabase.bookingDao()
    override suspend fun fetchByDate(atDate: String) = bookingDao.fetchByDate(atDate).map {
        BookingDomain(
            id = it.uuid,
            bookedClientId = it.bookedClientId,
            bookedClientName = it.bookedClientName,
            bookedClientAddress = it.bookedClientAddress,
            bookedDate = it.bookedDate,
            bookedTime = it.bookedTime,
            bookedService = it.bookedService
        )
    }

    override suspend fun add(
        bookedClientId: String,
        bookedClientName: String,
        bookedClientAddress: String,
        bookedDate: String,
        bookedTime: Long
    ) {
        val booking = Booking(
            uuid = UUID.randomUUID().toString(),
            bookedClientId = bookedClientId,
            bookedClientName = bookedClientName,
            bookedClientAddress = bookedClientAddress,
            bookedDate = bookedDate,
            bookedTime = bookedTime,
            bookedService = null
        )
        bookingDao.insert(
            booking
        )
    }
}