package com.rodcollab.cliq.core.repository

import com.rodcollab.cliq.core.model.BookingDomain

interface BookingRepository {

    suspend fun fetchAll(): List<BookingDomain>

    suspend fun add(bookedClientName: String, bookedDate: String, bookedTime: Long)

}