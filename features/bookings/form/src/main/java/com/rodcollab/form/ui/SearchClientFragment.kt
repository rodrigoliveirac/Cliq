package com.rodcollab.form.ui

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodcollab.cliq.core.ui.R.*
import com.rodcollab.cliq.features.bookings.form.databinding.FragmentSearchClientsBinding
import com.rodcollab.form.Utils
import com.rodcollab.form.ui.adapters.SearchClientListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchClientFragment : Fragment() {

    private var _binding: FragmentSearchClientsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SearchClientListAdapter
    private lateinit var viewModel: SearchClientViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SearchClientViewModel::class.java]
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

        menuCreate()

        setupSearchClientListAdapter()
        updateListAccordingToOnQueryChanged()
        viewModel.clientSelected().observe(viewLifecycleOwner) {
            if (it.wasSelected && it.clientSelected != null) {
                findNavController().navigate(com.rodcollab.cliq.core.ui.R.id.action_searchClient_to_bookingForm)
            }
        }
    }

    private fun menuCreate() {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(com.rodcollab.cliq.core.ui.R.menu.add_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == com.rodcollab.cliq.core.ui.R.id.overflowMenu) {
                    findNavController().navigate(com.rodcollab.cliq.core.ui.R.id.action_searchClient_to_clientForm)
                    return true
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    private fun updateListAccordingToOnQueryChanged() {

        viewModel.getLastClient()
            .observe(viewLifecycleOwner) {
                binding.searchViewClients.setQuery(it.name, false)
            }


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