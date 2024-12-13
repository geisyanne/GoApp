package com.geisyanne.goapp.ui.features.travelHistory

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.geisyanne.goapp.R
import com.geisyanne.goapp.data.request.Driver
import com.geisyanne.goapp.databinding.FragmentTravelHistoryBinding
import com.geisyanne.goapp.domain.model.RideModel
import com.geisyanne.goapp.util.setDebouncedOnClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class TravelHistoryFragment : Fragment(R.layout.fragment_travel_history) {

    private var _binding: FragmentTravelHistoryBinding? = null
    private val binding get() = _binding!!

    private val travelHistoryViewModel: TravelHistoryViewModel by viewModel()
    private lateinit var adapter: TravelHistoryAdapter

    private val drivers = listOf(
        Driver(1, "Homer Simpson"),
        Driver(2, "Dominic Toretto"),
        Driver(3, "James Bond")
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTravelHistoryBinding.bind(view)

        setupRecyclerView()
        setupSpinner()
        observeViewModel()

        binding.btnFilterHistory.setDebouncedOnClickListener { onFilterClick() }
    }

    private fun setupRecyclerView() {
        adapter = TravelHistoryAdapter()
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@TravelHistoryFragment.adapter
        }
    }

    private fun setupSpinner() {
        val driverNames = listOf(getString(R.string.select_driver)) + drivers.map { it.name }
        val spinnerAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, driverNames)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerHistory.adapter = spinnerAdapter
    }

    private fun onFilterClick() {
        val customerId = binding.editUserIdHistory.text.toString()

        if (customerId.isEmpty()) {
            showToast(R.string.error_customer_id_not_provided)
            return
        }

        val selectedDriverName = binding.spinnerHistory.selectedItem.toString()

        if (selectedDriverName == getString(R.string.select_driver)) {
            showToast(R.string.select_driver)
            return
        }

        val driverId = drivers.find { it.name == selectedDriverName }?.id
        fetchRideHistory(customerId, driverId)
    }

    private fun fetchRideHistory(customerId: String, driverId: Int?) {
        travelHistoryViewModel.getRideHistory(customerId, driverId)
    }

    private fun observeViewModel() {
        travelHistoryViewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is TravelHistoryState.Loading -> showLoading(true)
                is TravelHistoryState.Success -> handleSuccess(state.rideHistory.rides)
                is TravelHistoryState.Error -> handleError(state.resId)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun handleSuccess(rides: List<RideModel>?) {
        showLoading(false)
        val filteredRides = filterRides(rides.orEmpty())

        binding.txtEmptyListHistory.visibility =
            if (filteredRides.isEmpty()) View.VISIBLE else View.GONE

        adapter.updateData(filteredRides)
    }

    private fun handleError(errorResId: String?) {
        showLoading(false)
        val errorMessage = errorResId ?: getString(R.string.error_generic)
        showToast(errorMessage)
    }

    private fun filterRides(rides: List<RideModel>): List<RideModel> {
        val selectedDriverName = binding.spinnerHistory.selectedItem.toString()
        return if (selectedDriverName == getString(R.string.select_driver)) {
            rides
        } else {
            rides.filter { it.driver?.name == selectedDriverName }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showToast(resId: Int) {
        Toast.makeText(requireContext(), getString(resId), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
