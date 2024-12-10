package co.geisyanne.goapp.data.mapper

import co.geisyanne.goapp.data.response.DistanceResponse
import co.geisyanne.goapp.data.response.DurationResponse
import co.geisyanne.goapp.data.response.GeocodingLocationResponse
import co.geisyanne.goapp.data.response.GeocodingResultsResponse
import co.geisyanne.goapp.data.response.LegResponse
import co.geisyanne.goapp.data.response.LocalizedValuesResponse
import co.geisyanne.goapp.data.response.LocationResponse
import co.geisyanne.goapp.data.response.NavigationInstructionResponse
import co.geisyanne.goapp.data.response.PolylineResponse
import co.geisyanne.goapp.data.response.ReviewResponse
import co.geisyanne.goapp.data.response.RideEstimateResponse
import co.geisyanne.goapp.data.response.RideOptionResponse
import co.geisyanne.goapp.data.response.RouteDetailsResponse
import co.geisyanne.goapp.data.response.RouteLocationResponse
import co.geisyanne.goapp.data.response.RouteResponse
import co.geisyanne.goapp.data.response.StaticDurationResponse
import co.geisyanne.goapp.data.response.StepResponse
import co.geisyanne.goapp.data.response.ViewportResponse
import co.geisyanne.goapp.domain.model.DistanceModel
import co.geisyanne.goapp.domain.model.DurationModel
import co.geisyanne.goapp.domain.model.GeocodingLocationModel
import co.geisyanne.goapp.domain.model.GeocodingResultsModel
import co.geisyanne.goapp.domain.model.LegModel
import co.geisyanne.goapp.domain.model.LocalizedValuesModel
import co.geisyanne.goapp.domain.model.LocationModel
import co.geisyanne.goapp.domain.model.NavigationInstructionModel
import co.geisyanne.goapp.domain.model.PolylineModel
import co.geisyanne.goapp.domain.model.ReviewModel
import co.geisyanne.goapp.domain.model.RideEstimateModel
import co.geisyanne.goapp.domain.model.RideOptionModel
import co.geisyanne.goapp.domain.model.RouteDetailsModel
import co.geisyanne.goapp.domain.model.RouteLocationModel
import co.geisyanne.goapp.domain.model.RouteModel
import co.geisyanne.goapp.domain.model.StaticDurationModel
import co.geisyanne.goapp.domain.model.StepModel
import co.geisyanne.goapp.domain.model.ViewportModel

fun RideEstimateResponse.toDomain(): RideEstimateModel {
    return RideEstimateModel(
        origin = this.origin?.toDomain(),
        destination = this.destination?.toDomain(),
        distance = this.distance,
        duration = this.duration,
        options = this.options?.map { it.toDomain() },
        routeModel = this.routeResponse?.toDomain()
    )
}

fun LocationResponse.toDomain(): LocationModel {
    return LocationModel(
        latitude = this.latitude,
        longitude = this.longitude
    )
}

fun RideOptionResponse.toDomain(): RideOptionModel {
    return RideOptionModel(
        id = this.id,
        name = this.name,
        description = this.description,
        vehicle = this.vehicle,
        review = this.review?.toDomain(),
        value = this.value
    )
}

fun ReviewResponse.toDomain(): ReviewModel {
    return ReviewModel(
        rating = this.rating,
        comment = this.comment
    )
}

fun RouteResponse.toDomain(): RouteModel {
    return RouteModel(
        routes = this.routes?.map { it.toDomain() },
        geocodingResults = this.geocodingResults?.toDomain()
    )
}

fun RouteDetailsResponse.toDomain(): RouteDetailsModel {
    return RouteDetailsModel(
        legs = this.legs?.map { it.toDomain() },
        distanceMeters = this.distanceMeters,
        duration = this.duration,
        staticDuration = this.staticDuration,
        polyline = this.polyline?.toDomain(),
        description = this.description,
        warnings = this.warnings,
        viewport = this.viewport?.toDomain(),
        travelAdvisory = this.travelAdvisory,
        localizedValues = this.localizedValues?.toDomain(),
        routeLabels = this.routeLabels,
        polylineDetails = this.polylineDetails
    )
}

fun LegResponse.toDomain(): LegModel {
    return LegModel(
        distanceMeters = this.distanceMeters,
        duration = this.duration,
        staticDuration = this.staticDuration,
        polyline = this.polyline.toDomain(),
        startLocation = this.startLocation?.toDomain(),
        endLocation = this.endLocation?.toDomain(),
        steps = this.steps.map { it.toDomain() }
    )
}

fun PolylineResponse.toDomain(): PolylineModel {
    return PolylineModel(
        encodedPolyline = this.encodedPolyline
    )
}

fun RouteLocationResponse.toDomain(): RouteLocationModel {
    return RouteLocationModel(
        latLng = this.latLng?.toDomain()
    )
}

fun StepResponse.toDomain(): StepModel {
    return StepModel(
        distanceMeters = this.distanceMeters,
        staticDuration = this.staticDuration,
        polyline = this.polyline?.toDomain(),
        startLocation = this.startLocation?.toDomain(),
        endLocation = this.endLocation?.toDomain(),
        navigationInstruction = this.navigationInstruction?.toDomain(),
        localizedValues = this.localizedValues?.toDomain(),
        travelMode = this.travelMode
    )
}

fun NavigationInstructionResponse.toDomain(): NavigationInstructionModel {
    return NavigationInstructionModel(
        maneuver = this.maneuver,
        instructions = this.instructions
    )
}

fun ViewportResponse.toDomain(): ViewportModel {
    return ViewportModel(
        low = this.low?.toDomain(),
        high = this.high?.toDomain()
    )
}

fun LocalizedValuesResponse.toDomain(): LocalizedValuesModel {
    return LocalizedValuesModel(
        distance = this.distance?.toDomain(),
        duration = this.duration?.toDomain(),
        staticDuration = this.staticDuration?.toDomain()
    )
}

fun DistanceResponse.toDomain(): DistanceModel {
    return DistanceModel(
        text = this.text
    )
}

fun StaticDurationResponse.toDomain(): StaticDurationModel {
    return StaticDurationModel(
        text = this.text
    )
}

fun DurationResponse.toDomain(): DurationModel {
    return DurationModel(
        text = this.text
    )
}

fun GeocodingResultsResponse.toDomain(): GeocodingResultsModel {
    return GeocodingResultsModel(
        origin = this.origin?.toDomain(),
        destination = this.destination?.toDomain()
    )
}

fun GeocodingLocationResponse.toDomain(): GeocodingLocationModel {
    return GeocodingLocationModel(
        geocoderStatus = this.geocoderStatus,
        type = this.type,
        placeId = this.placeId
    )
}
