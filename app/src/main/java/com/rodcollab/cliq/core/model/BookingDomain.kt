package com.rodcollab.cliq.core.model

data class BookingDomain(
    val id: String,
    //val clientId: String,
    val bookedClientName: String,
    //val clientAddress: String,
   // val valueService: Int,
    val bookedDate: String,
    val bookedTime: Long
)