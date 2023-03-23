package com.rodcollab.clients.collections.ui

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.rodcollab.clients.collections.ui.adapters.ClientsAdapter
import com.rodcollab.cliq.features.clients.collections.databinding.FragmentClientListBinding
import com.rodcollab.cliq.core.ui.R.color
import com.rodcollab.cliq.core.ui.R.dimen
import com.rodcollab.cliq.core.ui.R.id.action_clientList_to_clientForm
import com.rodcollab.cliq.core.ui.R.id.overflowMenu
import com.rodcollab.cliq.features.clients.collections.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientListFragment : Fragment() {

    private var _binding: FragmentClientListBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: ClientsAdapter

    private lateinit var viewModel: ClientListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ClientListViewModel::class.java]
        lifecycle.addObserver(ClientListLifecycleObserver(viewModel))
        adapter = ClientsAdapter()
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

        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(com.rodcollab.cliq.core.ui.R.menu.add_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == overflowMenu) {
                    findNavController().navigate(action_clientList_to_clientForm)
                    return true
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        addingDividerDecoration()

        viewModel.stateOnceAndStream().observe(viewLifecycleOwner) { uiState ->
            bindUiState(uiState)
        }

    }

    private fun bindUiState(uiState: ClientListViewModel.UiState) {
        adapter.submitList(uiState.clientList)
    }

    private fun addingDividerDecoration() {
        // Adding Line between items with MaterialDividerItemDecoration
        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)

        // Removing the line at the end of the list
        divider.isLastItemDecorated = false

        val resources = requireContext().resources

        // Adding start spacing
        divider.dividerInsetStart = resources.getDimensionPixelSize(dimen.horizontal_margin)

        // Defining size of the line
        divider.dividerThickness = resources.getDimensionPixelSize(dimen.divider_height)
        divider.dividerColor = ContextCompat.getColor(requireContext(), color.primary_200)

        binding.clientRecyclerView.addItemDecoration(divider)
    }

//    private fun addingDividerSpace() {
//        binding.clientRecyclerView.addItemDecoration(ClientListItemDecoration(requireContext()))
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}