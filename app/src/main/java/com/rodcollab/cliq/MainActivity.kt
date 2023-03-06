package com.rodcollab.cliq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

val list = arrayOf<ClientItem>(
    ClientItem(
        id = "some",
        name = "Jeniffer Torres",
        reference = "Criativista Digital",
        address = "São Gerardo"
    ),
    ClientItem(
        id = "some",
        name = "Rodrigo Oliveira",
        reference = "Rodcollab",
        address = "São Gerardo"
    ),
)

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ClientsAdapter
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ClientsAdapter(list)

        recycler = findViewById(R.id.recycler_view)

        recycler.layoutManager = LinearLayoutManager(this)

        recycler.adapter = adapter


    }

}