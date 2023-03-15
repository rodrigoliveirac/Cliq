package com.rodcollab.cliq.core.model

data class BookingDomain(
    val id: String,
    val bookedClientId: String,
    val bookedClientName: String,
    val bookedClientAddress: String,
   // val valueService: Int,
    val bookedDate: String,
    val bookedTime: Long
)