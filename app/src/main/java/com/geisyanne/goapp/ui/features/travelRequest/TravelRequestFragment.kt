package com.geisyanne.goapp.ui.features.travelRequest


import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.geisyanne.goapp.R
import com.geisyanne.goapp.databinding.FragmentTravelRequestBinding
import com.geisyanne.goapp.ui.SharedViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TravelRequestFragment : Fragment(R.layout.fragment_travel_request) {

    private var _binding: FragmentTravelRequestBinding? = null
    private val binding get() = _binding!!

    private lateinit var customerId: String

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val travelRequestViewModel: TravelRequestViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTravelRequestBinding.bind(view)

        setupAutoCompleteTextViews()
        setupListeners()
        observeViewModel()
    }

    // PARA TESTES
    private fun setupAutoCompleteTextViews() {
        val addressOrigin = listOf(
            "Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031",
            "Av. Thomas Edison, 365 - Barra Funda, São Paulo - SP, 01140-000",
            "Av. Brasil, 2033 - Jardim America, São Paulo - SP, 01431-001"
        )
        val addressDestination = listOf(
            "Av. Paulista, 1538 - Bela Vista, São Paulo - SP, 01310-200"
        )

        val originAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            addressOrigin
        )
        val destinationAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            addressDestination
        )

        binding.editOriginRequest.setAdapter(originAdapter)
        binding.editDestinationRequest.setAdapter(destinationAdapter)
    }

    private fun setupListeners() {
        binding.btnEstimateValueRequest.setOnClickListener {
            val customerId = binding.editUserRequest.text.toString()
            val origin = binding.editOriginRequest.text.toString()
            val destination = binding.editDestinationRequest.text.toString()

            sharedViewModel.setTravelRequestData(customerId, origin, destination)
            travelRequestViewModel.getRideEstimate(customerId, origin, destination)
        }
    }

    private fun observeViewModel() {

        lifecycleScope.launch {
            travelRequestViewModel.uiState.collect { state ->
                when (state) {
                    is TravelRequestState.Idle -> {
                    }

                    is TravelRequestState.Loading -> {
                        binding.progressRequest.visibility = View.VISIBLE
                    }

                    is TravelRequestState.Success -> {
                        binding.progressRequest.visibility = View.GONE
                        sharedViewModel.setRideEstimate(state.rideEstimate)
                        findNavController().navigate(R.id.action_travelRequestFragment_to_travelOptionsFragment)
                    }

                    is TravelRequestState.Error -> {
                        binding.progressRequest.visibility = View.GONE
                        sharedViewModel.clearRideEstimate()
                        val errorMessage = state.resId
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
