package com.rodcollab.core.data.model

data class BookingDomain(
    val id: String,
    val bookedClientId: String,
    val bookedClientName: String,
    val bookedClientAddress: String,
    val bookedDate: String,
    val bookedTime: Long,
    val bookedService: String? = null,
)