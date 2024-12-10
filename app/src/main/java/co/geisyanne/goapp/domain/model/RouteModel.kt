package co.geisyanne.goapp.domain.model

data class RouteModel(
    val routes: List<RouteDetailsModel>?,
    val geocodingResults: GeocodingResultsModel?
)

data class RouteDetailsModel(
    val legs: List<LegModel>?,
    val distanceMeters: Int?,
    val duration: String?,
    val staticDuration: String?,
    val polyline: PolylineModel?,
    val description: String?,
    val warnings: List<String>?,
    val viewport: ViewportModel?,
    val travelAdvisory: Any?,
    val localizedValues: LocalizedValuesModel?,
    val routeLabels: List<String>?,
    val polylineDetails: Any?
)

data class LegModel(
    val distanceMeters: Int?,
    val duration: String?,
    val staticDuration: String?,
    val polyline: PolylineModel,
    val startLocation: RouteLocationModel?,
    val endLocation: RouteLocationModel?,
    val steps: List<StepModel>
)

data class PolylineModel(
    val encodedPolyline: String?
)

data class RouteLocationModel(
    val latLng: LocationModel?
)

data class StepModel(
    val distanceMeters: Int?,
    val staticDuration: String?,
    val polyline: PolylineModel?,
    val startLocation: RouteLocationModel?,
    val endLocation: RouteLocationModel?,
    val navigationInstruction: NavigationInstructionModel?,
    val localizedValues: LocalizedValuesModel?,
    val travelMode: String?
)

data class NavigationInstructionModel(
    val maneuver: String?,
    val instructions: String?
)

data class DistanceModel(
    val text: String?
)

data class StaticDurationModel(
    val text: String?
)

data class ViewportModel(
    val low: LocationModel?,
    val high: LocationModel?
)

data class LocalizedValuesModel(
    val distance: DistanceModel?,
    val duration: DurationModel?,
    val staticDuration: StaticDurationModel?
)

data class DurationModel(
    val text: String?
)

data class GeocodingResultsModel(
    val origin: GeocodingLocationModel?,
    val destination: GeocodingLocationModel?
)

data class GeocodingLocationModel(
    val geocoderStatus: Any?,
    val type: List<String>?,
    val placeId: String?
)
