package com.rodcollab.cliq.collections.bookings.model

data class BookingItem(
    val id: String,
    val bookedClientName: String,
    val bookedDate: String,
    val bookedTime: Long
)