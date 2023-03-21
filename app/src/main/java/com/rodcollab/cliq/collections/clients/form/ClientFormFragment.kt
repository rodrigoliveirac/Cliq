package com.rodcollab.cliq.collections.clients.form

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.rodcollab.cliq.databinding.FragmentClientFormBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientFormFragment : Fragment() {

    private var _binding: FragmentClientFormBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: ClientFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ClientFormViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClientFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.clientBirthdayTextInputEditText.setOnTouchListener { _, motionEvent ->
            if (MotionEvent.ACTION_UP == motionEvent.action) {
                setupMaterialDatePicker()
            }
            true
        }

        binding.saveButton.setOnClickListener {

            val clientName = binding.clientNameTextInput.editText?.text.toString()
            val clientLastName = binding.clientLastNameTextInput.editText?.text.toString()
            val clientAddress = binding.clientAddressTextInput.editText?.text.toString()
            val clientPhoneNumber = binding.clientPhoneNumberTextInput.editText?.text.toString()
            val clientBirthday = binding.clientBirthdayTextInput.editText?.text.toString()

            viewModel.addClient(clientName, clientLastName, clientAddress, clientPhoneNumber, clientBirthday)

            findNavController().navigateUp()
        }
    }

    private fun setupMaterialDatePicker() {
        val builderDatePicker: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.datePicker()

        val pickerDate = builderDatePicker.build()
        pickerDate.show(this.parentFragmentManager, "DATE_PICKER")

        pickerDate.addOnDismissListener {
            viewModel.saveValueDate(pickerDate.headerText)
        }

        pickerDate.addOnPositiveButtonClickListener {


            viewModel.getValueDate.observe(viewLifecycleOwner) {
                binding.clientBirthdayTextInputEditText.setText(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}