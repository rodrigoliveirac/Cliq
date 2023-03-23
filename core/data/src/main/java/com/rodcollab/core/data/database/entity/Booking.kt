package com.rodcollab.core.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booking")
data class Booking(
    @PrimaryKey val uuid: String,
    @ColumnInfo("booked_clientId") val bookedClientId: String,
    @ColumnInfo("booked_clientName") val bookedClientName: String,
    @ColumnInfo("booked_clientAddress") val bookedClientAddress: String,
    @ColumnInfo("booked_date") val bookedDate: String,
    @ColumnInfo("booked_time") val bookedTime: Long,
    @ColumnInfo("booked_service") val bookedService: String?,
)