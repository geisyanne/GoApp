package com.geisyanne.goapp.ui.features.travelOptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geisyanne.goapp.domain.usecase.GetRideConfirmUseCase
import com.geisyanne.goapp.domain.usecase.RideConfirmResult
import com.geisyanne.goapp.util.SingleLiveEvent
import kotlinx.coroutines.launch

class TravelOptionsViewModel(
    private val getRideConfirmUseCase: GetRideConfirmUseCase,
) : ViewModel() {

    private val _uiState = SingleLiveEvent<TravelOptionState>()
    val uiState: SingleLiveEvent<TravelOptionState> get() = _uiState

    fun getRideOption(
        customerId: String?,
        origin: String?,
        destination: String?,
        distance: Int?,
        duration: Int?,
        id: Int?,
        name: String?,
        rideOptionValue: Double?
    ) {
        _uiState.value = TravelOptionState.Loading

        viewModelScope.launch {
            val result = getRideConfirmUseCase(
                customerId = customerId,
                origin = origin,
                destination = destination,
                distance = distance,
                duration = duration,
                rideOptionId = id,
                rideOptionName = name,
                rideOptionValue = rideOptionValue
            )

            when (result) {
                is RideConfirmResult.Success -> {
                    _uiState.value = TravelOptionState.Success(result.rideConfirm)
                }
                is RideConfirmResult.Failure -> {
                    _uiState.value = TravelOptionState.Error(result.resId)
                }
            }
        }
    }
}

sealed class TravelOptionState {
    object Idle : TravelOptionState()
    object Loading : TravelOptionState()
    data class Success(val rideConfirm: String) : TravelOptionState()
    data class Error(val resId: String?) : TravelOptionState()
}
