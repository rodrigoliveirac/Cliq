package com.rodcollab.cliq.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodcollab.cliq.databinding.FragmentClientListBinding
import com.rodcollab.cliq.dummy.MockClients

class ClientListFragment : Fragment() {

    private var _binding: FragmentClientListBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: ClientsAdapter

    private lateinit var mockList: MockClients

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ClientsAdapter()
        mockList = MockClients
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClientListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clientRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.clientRecyclerView.adapter = adapter

        binding.fab.setOnClickListener {
            mockList.add()
            adapter.submitList(mockList.fetchClients())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}