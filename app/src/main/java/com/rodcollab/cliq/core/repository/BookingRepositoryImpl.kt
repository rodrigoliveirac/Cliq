package com.rodcollab.cliq.core.repository

import com.rodcollab.cliq.core.database.BookingDao
import com.rodcollab.cliq.core.database.entity.Booking
import com.rodcollab.cliq.core.model.BookingDomain
import java.util.*
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor(private val dao: BookingDao) : BookingRepository {

    override suspend fun fetchByDate(atDate: String) = dao.fetchByDate(atDate).map {
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
        dao.insert(
            booking
        )
    }
}