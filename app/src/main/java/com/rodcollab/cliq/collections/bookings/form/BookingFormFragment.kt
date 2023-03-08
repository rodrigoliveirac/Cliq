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
import com.rodcollab.cliq.collections.clients.domain.GetClientsUseCaseImpl
import com.rodcollab.cliq.collections.clients.form.SearchClientListAdapter
import com.rodcollab.cliq.core.Utils
import com.rodcollab.cliq.core.repository.BookingRepositoryImpl
import com.rodcollab.cliq.core.repository.ClientRepositoryImpl
import com.rodcollab.cliq.databinding.FragmentBookingFormBinding

class BookingFormFragment : Fragment() {

    private var _binding: FragmentBookingFormBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SearchClientListAdapter

    private val viewModel: BookingFormViewModel by activityViewModels {

        val clientRepository = ClientRepositoryImpl
        val bookingsRepository = BookingRepositoryImpl

        val getClientsUseCase = GetClientsUseCaseImpl(clientRepository)
        val onQueryTextChangeUseCase = OnQueryTextChangeUseCaseImpl(getClientsUseCase)

        BookingFormViewModel.Factory(onQueryTextChangeUseCase, bookingsRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SearchClientListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchViewRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchViewRecyclerView.adapter = adapter

        val divider = Utils(requireContext()).addingDividerDecoration()

        binding.searchViewRecyclerView.addItemDecoration(divider)

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
        saveNewBooking()
    }

    private fun saveNewBooking() {
        binding.saveButton.setOnClickListener {
            val bookedTime = binding.bookingTimeTextInput.editText?.text.toString()
            val bookedClientName = binding.clientNameTextInput.editText?.text.toString()
            val bookedDate = binding.bookingDateTextInput.editText?.text.toString()

            viewModel.addBooking(bookedTime, bookedClientName, bookedDate)

            findNavController().navigateUp()
        }
    }
}