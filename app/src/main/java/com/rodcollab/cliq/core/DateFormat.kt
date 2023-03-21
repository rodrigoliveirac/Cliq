package com.rodcollab.cliq.core

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


object DateFormat {

    fun formatDate(dateSelected: Long): String {
        val date = getDate(dateSelected)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault())
        return LocalDate.parse(formatter.format(date.toInstant()).toString(), formatter)
            .toString()
    }

     fun localDateToString(localDate: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
        return formatter.format(localDate)
    }

    fun toLocalDate(datePicked: Long): LocalDate {
        val date = getDate(datePicked)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault())
        return LocalDate.parse(formatter.format(date.toInstant()).toString(), formatter)
    }

    private fun getDate(dateSelected: Long): Date {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateSelected
        return calendar.time
    }
}
