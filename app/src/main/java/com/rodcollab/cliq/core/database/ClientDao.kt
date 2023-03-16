package com.rodcollab.cliq.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rodcollab.cliq.core.database.entity.Client

@Dao
interface ClientDao {

    @Query("SELECT * FROM client")
    suspend fun fetchAllClients(): List<Client>

    @Insert
    suspend fun insert(clientId: Client)
}