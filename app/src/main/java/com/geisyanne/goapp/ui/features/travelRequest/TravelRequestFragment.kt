package com.geisyanne.goapp.ui.features.travelRequest

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.geisyanne.goapp.databinding.FragmentTravelRequestBinding
import com.geisyanne.goapp.R



import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TravelRequestFragment : Fragment(R.layout.fragment_travel_request) {

    private var _binding: FragmentTravelRequestBinding? = null
    private val binding get() = _binding!!

    private val travelRequestViewModel: TravelRequestViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTravelRequestBinding.bind(view)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.btnEstimateValueRequest.setOnClickListener {
            val customerId = binding.editUserRequest.text.toString()
            val origin = binding.editOriginRequest.text.toString()
            val destination = binding.editDestinationRequest.text.toString()

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
                        Toast.makeText(requireContext(), "Estimativa de viagem: ${state.rideEstimate}", Toast.LENGTH_SHORT).show()
                    }

                    is TravelRequestState.Error -> {
                        binding.progressRequest.visibility = View.GONE

                        val errorMessage = getString(state.resId)
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
