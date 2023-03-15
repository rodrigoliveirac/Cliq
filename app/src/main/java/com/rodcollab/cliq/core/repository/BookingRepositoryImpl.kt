package com.rodcollab.cliq.core.repository

import com.rodcollab.cliq.core.model.BookingDomain
import java.util.*

object BookingRepositoryImpl : BookingRepository {

    private val bookingListCache: MutableList<BookingDomain> = mutableListOf()

    override suspend fun fetchAll() = bookingListCache
    override suspend fun add(bookedClientId: String, bookedClientName: String, bookedDate: String, bookedTime: Long) {
        bookingListCache.add(
            BookingDomain(
                id = UUID.randomUUID().toString(),
                bookedClientId = bookedClientId,
                bookedClientName = bookedClientName,
                bookedDate = bookedDate,
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

object BrToUs {
    fun format(bookedDate: String): String {
        val day: String
        val month: String
        val year: String

        val dateSlice = bookedDate.split(" ").slice(0..4 step 2)

        day = dateSlice[0]
        month = dateSlice[1].slice(0..2)
        year = dateSlice[2]

        val inputDateString = "$month $day, $year"
        val inputDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        val date = inputDateFormat.parse(inputDateString)

        return outputDateFormat.format(date!!)
    }
}