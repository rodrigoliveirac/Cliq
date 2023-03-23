package com.rodcollab.core.data.repository

import com.rodcollab.core.data.model.BookingDomain

interface BookingRepository {

    suspend fun fetchByDate(atDate: String): List<BookingDomain>

    suspend fun add(bookedClientId: String, bookedClientName: String, bookedClientAddress:String, bookedDate: String, bookedTime: Long)

}