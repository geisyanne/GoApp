package com.geisyanne.goapp.ui.features.travelRequest

import com.geisyanne.goapp.domain.usecase.GetRideEstimateUseCase
import com.geisyanne.goapp.domain.usecase.RideEstimateResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class TravelRequestViewModel(
    private val getRideEstimateUseCase: GetRideEstimateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<TravelRequestState>(TravelRequestState.Idle)
    val uiState: StateFlow<TravelRequestState> get() = _uiState

    fun getRideEstimate(customerId: String, origin: String, destination: String) {

        _uiState.value = TravelRequestState.Loading

        viewModelScope.launch {
            when (val result = getRideEstimateUseCase(customerId, origin, destination)) {
                is RideEstimateResult.Success -> {
                    _uiState.value = TravelRequestState.Success(result)
                }
                is RideEstimateResult.Failure -> {
                    _uiState.value = TravelRequestState.Error(result.resId)
                }
            }
        }
    }
}

sealed class TravelRequestState {
    data object Idle : TravelRequestState()
    data object Loading : TravelRequestState()
    data class Success(val rideEstimate: RideEstimateResult) : TravelRequestState()
    data class Error(val resId: Int) : TravelRequestState()
}