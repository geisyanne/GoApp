package co.geisyanne.goapp.data.response

data class RideEstimateResponse(
    val origin: LocationResponse? = null,
    val destination: LocationResponse? = null,
    val distance: Int? = null,
    val duration: Int? = null,
    val options: List<RideOptionResponse>? = null,
    val routeResponse: RouteResponse? = null
)

data class LocationResponse(
    val latitude: Double? = null,
    val longitude: Double? = null
)

data class RideOptionResponse(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val vehicle: String? = null,
    val review: ReviewResponse? = null,
    val value: Double? = null
)

data class ReviewResponse(
    val rating: Int? = null,
    val comment: String? = null
)
