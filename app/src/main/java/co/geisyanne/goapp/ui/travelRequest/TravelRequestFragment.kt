package co.geisyanne.goapp.ui.travelRequest

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import co.geisyanne.goapp.R
import co.geisyanne.goapp.data.request.RideEstimateRequest
import co.geisyanne.goapp.databinding.FragmentTravelRequestBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TravelRequestFragment : Fragment(R.layout.fragment_travel_request) {

    private var _binding: FragmentTravelRequestBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TravelRequestViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTravelRequestBinding.bind(view)

        observeViewModel()
        requestRideEstimate()
    }

    private fun observeViewModel() {
        viewModel.rideEstimate.observe(viewLifecycleOwner) { estimate ->
            // Atualizar a UI com a estimativa
            Toast.makeText(requireContext(), "Distância: ${estimate.distance}, Tempo: ${estimate.duration}", Toast.LENGTH_LONG).show()
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            // Mostrar erro
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestRideEstimate() {
        val request = RideEstimateRequest(
            "1",
            "Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031",
            "Av. Paulista, 1538 - Bela Vista, São Paulo - SP, 01310-200"
        )
        viewModel.fetchRideEstimate(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
