package com.geisyanne.goapp.ui.features.travelOptions

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.geisyanne.goapp.R
import com.geisyanne.goapp.databinding.FragmentTravelOptionsBinding
import com.geisyanne.goapp.domain.model.LocationModel
import com.geisyanne.goapp.domain.model.RideOptionModel
import com.geisyanne.goapp.domain.model.RouteModel
import com.geisyanne.goapp.domain.usecase.RideEstimateResult
import com.geisyanne.goapp.ui.SharedViewModel
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
        val rideEstimate = sharedViewModel.getRideEstimate()
        if (rideEstimate is RideEstimateResult.Success) {
            val rideOptions = rideEstimate.rideEstimate.options.orEmpty()

            setupStaticMapView(rideEstimate.rideEstimate.routeModel)

            adapter.updateData(rideOptions)
            updateVisibility(rideOptions)
        } else {
            updateVisibility(emptyList())
        }
    }

    private fun setupStaticMapView(routeModel: RouteModel?) {
        if (routeModel != null) {
            val origin =
                routeModel.routes?.firstOrNull()?.legs?.firstOrNull()?.startLocation?.latLng
            val destination =
                routeModel.routes?.firstOrNull()?.legs?.firstOrNull()?.endLocation?.latLng

            if (origin != null && destination != null) {
                val url = buildStaticMapUrl(origin, destination, routeModel)

                loadStaticMap(url)
            }
        }
    }

    private fun buildStaticMapUrl(
        origin: LocationModel,
        destination: LocationModel,
        routeModel: RouteModel
    ): String {
        val originLatLng = "${origin.latitude},${origin.longitude}"
        val destinationLatLng = "${destination.latitude},${destination.longitude}"
        val polyline = routeModel.routes?.firstOrNull()?.polyline?.encodedPolyline ?: ""
        val zoom = 10
        val markers = "markers=$originLatLng&markers=$destinationLatLng"
        val path = "path=weight:3|color:blue|enc:$polyline"
        val key = "key=${getString(R.string.maps_api_key)}"

        return "https://maps.googleapis.com/maps/api/staticmap?center=$originLatLng&zoom=$zoom&size=600x300&$markers&$path&$key"
    }

    private fun loadStaticMap(url: String) {
        Glide.with(requireContext())
            .load(url)
            .error(R.drawable.placeholder_map)
            .placeholder(R.drawable.placeholder_map)
            .into(binding.imgMapOptions)
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
    }

    private fun observeViewModel() {
        travelOptionsViewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is TravelOptionState.Loading -> {
                    binding.progressLoading.visibility = View.VISIBLE
                }

                is TravelOptionState.Success -> {
                    binding.progressLoading.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        state.rideConfirm,
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_OptionsFragment_to_HistoryFragment)
                }

                is TravelOptionState.Error -> {
                    binding.progressLoading.visibility = View.GONE
                    val errorMessage = state.resId
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
