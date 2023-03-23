package com.rodcollab.cliq.features.bookings.collections.model

data class BookingItem(
    val id: String,
    val bookedClientName: String,
    val bookedClientAddress: String,
    val bookedDate: String,
    val bookedTime: Long
)