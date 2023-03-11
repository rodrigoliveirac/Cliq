package com.rodcollab.cliq.core.repository

import com.rodcollab.cliq.core.model.BookingDomain

interface BookingRepository {

    fun fetchAll(): List<BookingDomain>

    fun add(bookedClientName: String, bookedDate: String, bookedTime: Long)

}