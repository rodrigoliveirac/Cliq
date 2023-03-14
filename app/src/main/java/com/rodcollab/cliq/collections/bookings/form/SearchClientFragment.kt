package com.rodcollab.cliq.collections.bookings.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodcollab.cliq.R
import com.rodcollab.cliq.collections.clients.domain.GetClientsUseCaseImpl
import com.rodcollab.cliq.collections.clients.form.SearchClientListAdapter
import com.rodcollab.cliq.core.Utils
import com.rodcollab.cliq.core.repository.ClientRepositoryImpl
import com.rodcollab.cliq.databinding.FragmentSearchClientsBinding

class SearchClientFragment : Fragment() {

    private var _binding: FragmentSearchClientsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SearchClientListAdapter
    private val viewModel: SearchClientViewModel by activityViewModels {
        val clientRepository = ClientRepositoryImpl
        val getClientsUseCase = GetClientsUseCaseImpl(clientRepository)
        val onQueryTextChangeUseCase = OnQueryTextChangeUseCaseImpl(getClientsUseCase)
        SearchClientViewModel.Factory(getClientsUseCase, onQueryTextChangeUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SearchClientListAdapter(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchClientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchClientListAdapter()
        updateListAccordingToOnQueryChanged()
        viewModel.clientSelected().observe(viewLifecycleOwner) {
            if (it.wasSelected && it.clientSelected != null) {
                findNavController().navigate(R.id.action_searchClient_to_bookingForm)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSearchClientListAdapter() {
        binding.searchViewRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchViewRecyclerView.adapter = adapter

        val divider = Utils(requireContext()).addingDividerDecoration()

        binding.searchViewRecyclerView.addItemDecoration(divider)

    }

    private fun updateListAccordingToOnQueryChanged() {
        binding.searchViewClients.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                viewModel.onQueryTextChange(newText.toString())
                viewModel.stateOnceAndStream().observe(viewLifecycleOwner) {
                    adapter.submitList(it.clientList)
                }

                return false
            }

        })


    }
}