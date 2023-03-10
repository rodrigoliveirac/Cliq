package com.rodcollab.cliq.collections.clients.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rodcollab.cliq.core.repository.ClientRepositoryImpl
import com.rodcollab.cliq.databinding.FragmentClientFormBinding

class ClientFormFragment : Fragment() {

    private var _binding: FragmentClientFormBinding? = null

    private val binding get() = _binding!!

    private val viewModel: ClientFormViewModel by activityViewModels {
        ClientFormViewModel.Factory(ClientRepositoryImpl)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClientFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {

            val clientName = binding.clientNameTextInput.editText?.text.toString()
            val clientReference = binding.referenceTextInput.editText?.text.toString()

            viewModel.addClient(clientName, clientReference)

            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}