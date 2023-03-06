package com.rodcollab.cliq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rodcollab.cliq.dummy.MockClients

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ClientsAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var floatBtn: FloatingActionButton
    private lateinit var mockList: MockClients

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        mockList = MockClients

        adapter = ClientsAdapter()

        recycler = findViewById(R.id.recycler_view)

        floatBtn = findViewById(R.id.fab)

        recycler.layoutManager = LinearLayoutManager(this)

        recycler.adapter = adapter

        floatBtn.setOnClickListener {
            mockList.add()
            adapter.submitList(mockList.fetchClients())
        }
    }
}