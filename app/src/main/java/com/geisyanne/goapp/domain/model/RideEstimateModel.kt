package com.geisyanne.goapp.domain.model

data class RideEstimateModel(
    val origin: LocationModel?,
    val destination: LocationModel?,
    val distance: Int?,
    val duration: Int?,
    val options: List<RideOptionModel>?,
    val routeModel: RouteModel?
)

data class LocationModel(
    val latitude: Double?,
    val longitude: Double?
)

data class RideOptionModel(
    val id: Int?,
    val name: String?,
    val description: String?,
    val vehicle: String?,
    val review: ReviewModel?,
    val value: Double?
)

data class ReviewModel(
    val rating: Int?,
    val comment: String?
)

