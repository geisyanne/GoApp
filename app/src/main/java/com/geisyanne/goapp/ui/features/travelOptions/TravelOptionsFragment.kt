package com.geisyanne.goapp.ui.features.travelOptions

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.geisyanne.goapp.R
import com.geisyanne.goapp.databinding.FragmentTravelOptionsBinding
import com.geisyanne.goapp.domain.model.RideOptionModel
import com.geisyanne.goapp.domain.usecase.RideEstimateResult
import com.geisyanne.goapp.ui.SharedViewModel

class TravelOptionsFragment : Fragment(R.layout.fragment_travel_options) {

    private var _binding: FragmentTravelOptionsBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var adapter: TravelOptionsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTravelOptionsBinding.bind(view)

        setupRecyclerView()
        populateRideOptions()
    }

    private fun setupRecyclerView() {
        adapter = TravelOptionsAdapter(emptyList()) { selectedRideOption ->
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
        Toast.makeText(
            requireContext(),
            "Você escolheu: ${selectedRideOption.name}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
