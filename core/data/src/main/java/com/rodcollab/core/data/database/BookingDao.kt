package com.rodcollab.core.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rodcollab.core.data.database.entity.Booking

@Dao
interface BookingDao {

    @Query("SELECT * FROM booking WHERE booked_date LIKE '%'||:date||'%'")
    suspend fun fetchByDate(date: String): List<Booking>

    @Insert
    suspend fun insert(bookingId: Booking)
}