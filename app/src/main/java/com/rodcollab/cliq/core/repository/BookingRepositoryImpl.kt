package com.rodcollab.cliq.core.repository

import com.rodcollab.cliq.core.BrToUs
import com.rodcollab.cliq.core.model.BookingDomain
import java.text.SimpleDateFormat
import java.util.*

object BookingRepositoryImpl : BookingRepository {

    private val bookingListCache: MutableList<BookingDomain> = mutableListOf(
        BookingDomain(
            id = UUID.randomUUID().toString(),
            bookedClientId = "bookedClientId",
            bookedClientName = "rodrigo",
            bookedClientAddress = "Rua frederico severo, 201",
            bookedDate = "2023-03-15",
            bookedTime = 60000L,
        ),
        BookingDomain(
            id = UUID.randomUUID().toString(),
            bookedClientId = "bookedClientId",
            bookedClientName = "jeni",
            bookedClientAddress = "Rua frederico severo, 201",
            bookedDate = "2023-03-15",
            bookedTime = 70000L,
        ),
        BookingDomain(
            id = UUID.randomUUID().toString(),
            bookedClientId = "bookedClientId",
            bookedClientName = "francisco",
            bookedClientAddress = "Rua frederico severo, 201",
            bookedDate = "2023-03-16",
            bookedTime = 60000L,
        ),
        BookingDomain(
            id = UUID.randomUUID().toString(),
            bookedClientId = "bookedClientId",
            bookedClientName = "roxy",
            bookedClientAddress = "Rua frederico severo, 201",
            bookedDate = "2023-03-14",
            bookedTime = 60000L,
        )
    )

    override suspend fun fetchAll() = bookingListCache
    override suspend fun fetch(atDate: String) = bookingListCache.filter { it.bookedDate == atDate }

    override suspend fun add(
        bookedClientId: String,
        bookedClientName: String,
        bookedClientAddress: String,
        bookedDate: String,
        bookedTime: Long
    ) {
        bookingListCache.add(
            BookingDomain(
                id = UUID.randomUUID().toString(),
                bookedClientId = bookedClientId,
                bookedClientName = bookedClientName,
                bookedClientAddress = bookedClientAddress,
                bookedDate = formattedDate(bookedDate),
                bookedTime = bookedTime,
            )
        )
    }

    private fun formattedDate(bookedDate: String) : String {
       return when(Locale.getDefault().language) {
            "pt" -> BrToUs.format(bookedDate)
             else -> getDefaultUS(bookedDate)
        }


    }
    private fun getDefaultUS(bookedDate: String): String {
        val inputDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val date = inputDateFormat.parse(bookedDate)
        return outputDateFormat.format(date!!)
    }
}