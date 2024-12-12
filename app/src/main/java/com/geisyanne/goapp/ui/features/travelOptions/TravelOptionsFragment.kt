package com.geisyanne.goapp.ui.features.travelOptions

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.geisyanne.goapp.R
import com.geisyanne.goapp.databinding.FragmentTravelOptionsBinding
import com.geisyanne.goapp.domain.model.RideOptionModel
import com.geisyanne.goapp.domain.usecase.RideEstimateResult
import com.geisyanne.goapp.ui.SharedViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TravelOptionsFragment : Fragment(R.layout.fragment_travel_options) {

    private var _binding: FragmentTravelOptionsBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val travelOptionsViewModel: TravelOptionsViewModel by viewModel()

    private lateinit var adapter: TravelOptionsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTravelOptionsBinding.bind(view)

        setupRecyclerView()
        populateRideOptions()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = TravelOptionsAdapter { selectedRideOption ->
            onRideOptionSelected(selectedRideOption)
        }
        binding.rvRideOptions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@TravelOptionsFragment.adapter
        }
    }

    private fun populateRideOptions() {
        Log.d("TravelOptionsFragment", "RideEstimate: ${sharedViewModel.getRideEstimate()}")
        val rideEstimate = sharedViewModel.getRideEstimate()
        if (rideEstimate is RideEstimateResult.Success) {
            val rideOptions = rideEstimate.rideEstimate.options.orEmpty()
            adapter.updateData(rideOptions)
            updateVisibility(rideOptions)
        } else {
            updateVisibility(emptyList())
        }
    }

    private fun updateVisibility(rideOptions: List<RideOptionModel>) {
        binding.txtEmptyListOptions.visibility =
            if (rideOptions.isEmpty()) View.VISIBLE else View.GONE
        binding.rvRideOptions.visibility = if (rideOptions.isNotEmpty()) View.VISIBLE else View.GONE
    }

    private fun onRideOptionSelected(selectedRideOption: RideOptionModel) {

        val travelRequestData = sharedViewModel.getTravelRequestData()
        if (travelRequestData != null) {
            val customerId = travelRequestData.customerId
            val origin = travelRequestData.origin
            val destination = travelRequestData.destination

            val rideEstimate = sharedViewModel.getRideEstimate()
            if (rideEstimate is RideEstimateResult.Success) {
                val rideEstimateModel = rideEstimate.rideEstimate
                val distance = rideEstimateModel.distance
                val duration = rideEstimateModel.duration
                val id = selectedRideOption.id
                val name = selectedRideOption.name
                val rideOptionValue = selectedRideOption.value

                travelOptionsViewModel.getRideOption(
                    customerId = customerId,
                    origin = origin,
                    destination = destination,
                    distance = distance,
                    duration = duration,
                    id = id,
                    name = name,
                    rideOptionValue = rideOptionValue
                )
            }
        }


        Toast.makeText(
            requireContext(),
            "Você escolheu: ${selectedRideOption.name}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun observeViewModel() {

        lifecycleScope.launch {
            travelOptionsViewModel.uiState.collect { state ->
                when (state) {
                    is TravelOptionState.Idle -> {
                    }

                    is TravelOptionState.Loading -> {
                        binding.progressLoading.visibility = View.VISIBLE
                    }

                    is TravelOptionState.Success -> {
                        binding.progressLoading.visibility = View.GONE
                        //sharedViewModel.setRideEstimate(state.rideEstimate)
                        Toast.makeText(
                            requireContext(),
                            state.rideConfirm,
                            Toast.LENGTH_SHORT
                        ).show()
                        //findNavController().navigate(R.id.action_travelRequestFragment_to_travelOptionsFragment)
                    }

                    is TravelOptionState.Error -> {
                        binding.progressLoading.visibility = View.GONE
                        val errorMessage = state.resId
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT)
                            .show()
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
