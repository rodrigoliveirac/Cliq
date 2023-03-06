package com.rodcollab.cliq.dummy

import com.rodcollab.cliq.collections.ClientItem
import com.rodcollab.cliq.core.ClientsRepository
import java.util.*

object MockClients : ClientsRepository {

    private var clientsList: MutableList<ClientItem> = mutableListOf()

    private val randomClientList = listOf(
        ClientItem(
            id = UUID.randomUUID().toString(),
            name = "Regina Oliveira",
            reference = "Tia do Rodrigo",
            address = "Messejana"
        ),
        ClientItem(
            id = UUID.randomUUID().toString(),
            name = "Rodrigo Oliveira",
            reference = "Rodcollab",
            address = "Messejana"
        ),
        ClientItem(
            id = UUID.randomUUID().toString(),
            name = "Jeniffer Torres",
            reference = "Criativista Digital",
            address = "São Gerardo"
        ),
        ClientItem(
            id = UUID.randomUUID().toString(),
            name = "Ronney Oliveira",
            reference = "Car Advice",
            address = "Cambeba"
        ),
        ClientItem(
            id = UUID.randomUUID().toString(),
            name = "Rosângela Oliveira",
            reference = "Rosa Doces",
            address = "Aerolândia"
        ),
        ClientItem(
            id = UUID.randomUUID().toString(),
            name = "Didi",
            reference = "Tropicar",
            address = "Aquiraz"
        ),
        ClientItem(
            id = UUID.randomUUID().toString(),
            name = "Raphael Oliveira",
            reference = "Avox",
            address = "Messejana"
        ),
    )

    override fun add() {
        clientsList.add(randomClientList.map { it }.random())
    }

    override fun fetchClients(): List<ClientItem> {
        return clientsList.map { it.copy() }
    }


}