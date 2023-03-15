package com.rodcollab.cliq.core

import java.text.SimpleDateFormat
import java.util.*

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