package com.rodcollab.cliq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodcollab.cliq.collections.ClientsAdapter
import com.rodcollab.cliq.databinding.ActivityMainBinding
import com.rodcollab.cliq.dummy.MockClients

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ClientsAdapter
    private lateinit var mockList: MockClients
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        mockList = MockClients

        adapter = ClientsAdapter()

        val recycler = binding.recyclerView

        recycler.layoutManager = LinearLayoutManager(this)

        recycler.adapter = adapter


        binding.fab.setOnClickListener {
            mockList.add()
            adapter.submitList(mockList.fetchClients())
        }
    }
}