package com.rodcollab.cliq.collections.bookings.model

data class BookingItem(
    val id: String,
    val clientName: String,
    val bookedDate: String,
    val bookedTime: Long
)