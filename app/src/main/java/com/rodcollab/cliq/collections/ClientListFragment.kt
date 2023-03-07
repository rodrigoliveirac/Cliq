package com.rodcollab.cliq.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.rodcollab.cliq.R
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

        addingDividerDecoration()

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_clientList_to_clientForm)
        }

    }

    private fun addingDividerDecoration() {
        // Adding Line between items with MaterialDividerItemDecoration
        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)

        // Removing the line at the end of the list
        divider.isLastItemDecorated = false

        val resources = requireContext().resources

        // Adding start spacing
        divider.dividerInsetStart = resources.getDimensionPixelSize(R.dimen.horizontal_margin)

        // Defining size of the line
        divider.dividerThickness = resources.getDimensionPixelSize(R.dimen.divider_height)
        divider.dividerColor = ContextCompat.getColor(requireContext(), R.color.primary_200)

        binding.clientRecyclerView.addItemDecoration(divider)
    }

    private fun addingDividerSpace() {
        binding.clientRecyclerView.addItemDecoration(ClientListItemDecoration(requireContext()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        adapter.submitList(mockList.fetchClients())
    }
}