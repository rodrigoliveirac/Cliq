package com.rodcollab.cliq.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rodcollab.cliq.core.database.entity.Booking

@Dao
interface BookingDao {

    @Query("SELECT * FROM booking WHERE booked_date LIKE '%'||:date||'%'")
    suspend fun fetchByDate(date: String): List<Booking>

    @Insert
    suspend fun insert(bookingId: Booking)
}