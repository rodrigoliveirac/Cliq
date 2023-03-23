package com.rodcollab.core.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "client")
data class Client(
    @PrimaryKey val uuid: String,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("lastName") val lastName: String,
    @ColumnInfo("address") val address: String,
    @ColumnInfo("phoneNumber") val phoneNumber: String,
    @ColumnInfo("birthday") val birthday: String,
)