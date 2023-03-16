package com.rodcollab.cliq.core.repository

import com.rodcollab.cliq.core.model.BookingDomain

interface BookingRepository {

    suspend fun fetchByDate(atDate: String): List<BookingDomain>

    suspend fun add(bookedClientId: String, bookedClientName: String, bookedClientAddress:String, bookedDate: String, bookedTime: Long)

}