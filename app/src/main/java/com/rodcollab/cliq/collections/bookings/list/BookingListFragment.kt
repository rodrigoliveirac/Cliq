package com.rodcollab.cliq.collections.bookings.list

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodcollab.cliq.R
import com.rodcollab.cliq.collections.bookings.adapters.BookingsAdapter
import com.rodcollab.cliq.collections.bookings.domain.GetBookingsUseCaseImpl
import com.rodcollab.cliq.core.repository.BookingRepositoryImpl
import com.rodcollab.cliq.databinding.FragmentBookingListBinding

class BookingListFragment : Fragment() {

    private var _binding: FragmentBookingListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: BookingsAdapter

    private val viewModel: BookingListViewModel by activityViewModels {
        val bookingsRepository = BookingRepositoryImpl
        val getBookingsUseCase = GetBookingsUseCaseImpl(bookingsRepository)

        BookingListViewModel.Factory(getBookingsUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(BookingListLifecycleObserver(viewModel))
        adapter = BookingsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        menuCreate()

        observeList()
    }

    private fun observeList() {
        viewModel.stateOnceAndStream().observe(viewLifecycleOwner) { uiState ->
            bindUiState(uiState)
        }
    }

    private fun setupAdapter() {
        binding.bookingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.bookingRecyclerView.adapter = adapter
    }

    private fun menuCreate() {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.add_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.overflowMenu) {
                    findNavController().navigate(R.id.action_bookingList_to_searchClientList)
                    return true
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun bindUiState(uiState: BookingListViewModel.UiState) {
        adapter.submitList(uiState.bookingList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}