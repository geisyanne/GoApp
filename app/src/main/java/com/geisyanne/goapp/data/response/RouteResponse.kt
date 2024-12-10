package com.geisyanne.goapp.data.response

data class RouteResponse(
    val routes: List<RouteDetailsResponse>? = null,
    val geocodingResults: GeocodingResultsResponse? = null
)

data class RouteDetailsResponse(
    val legs: List<LegResponse>? = null,
    val distanceMeters: Int? = null,
    val duration: String? = null,
    val staticDuration: String? = null,
    val polyline: PolylineResponse? = null,
    val description: String? = null,
    val warnings: List<String>? = null,
    val viewport: ViewportResponse? = null,
    val travelAdvisory: Any? = null,
    val localizedValues: LocalizedValuesResponse? = null,
    val routeLabels: List<String>? = null,
    val polylineDetails: Any? = null
)

data class LegResponse(
    val distanceMeters: Int? = null,
    val duration: String? = null,
    val staticDuration: String? = null,
    val polyline: PolylineResponse,
    val startLocation: RouteLocationResponse? = null,
    val endLocation: RouteLocationResponse? = null,
    val steps: List<StepResponse>
)

data class PolylineResponse(
    val encodedPolyline: String? = null
)

data class RouteLocationResponse(
    val latLng: LocationResponse? = null
)

data class StepResponse(
    val distanceMeters: Int? = null,
    val staticDuration: String? = null,
    val polyline: PolylineResponse? = null,
    val startLocation: RouteLocationResponse? = null,
    val endLocation: RouteLocationResponse? = null,
    val navigationInstruction: NavigationInstructionResponse? = null,
    val localizedValues: LocalizedValuesResponse? = null,
    val travelMode: String? = null
)

data class NavigationInstructionResponse(
    val maneuver: String? = null,
    val instructions: String? = null,
)

data class DistanceResponse(
    val text: String? = null
)

data class StaticDurationResponse(
    val text: String? = null
)

data class ViewportResponse(
    val low: LocationResponse? = null,
    val high: LocationResponse? = null
)

data class LocalizedValuesResponse(
    val distance: DistanceResponse? = null,
    val duration: DurationResponse? = null,
    val staticDuration: StaticDurationResponse? = null
)

data class DurationResponse(
    val text: String? = null
)

data class GeocodingResultsResponse(
    val origin: GeocodingLocationResponse? = null,
    val destination: GeocodingLocationResponse? = null
)

data class GeocodingLocationResponse(
    val geocoderStatus: Any? = null,
    val type: List<String>? = null,
    val placeId: String? = null
)



