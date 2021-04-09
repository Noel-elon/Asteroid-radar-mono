package com.udacity.asteroidradar.api

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
data class AsteroidRemote(
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String,
    @Json(name = "close_approach_data")
    val close_approach_data: List<CloseApproachDate>,
    @Json(name = "asteroid_distanceFromEarth")
    val distanceFromEarth: Double,
    @Json(name = "absolute_magnitude_h")
    val absolute_magnitude_h: Double,
    @Json(name = "estimated_diameter")
    val estimated_diameter: EstimatedDiameter,
    @Json(name = "is_potentially_hazardous_asteroid")
    val is_potentially_hazardous_asteroid: Boolean
) : Parcelable {
    val closeApproachDate: String
        get() = close_approach_data.get(0).close_approach_date
}


@Parcelize
data class CloseApproachDate(
    @Json(name = "close_approach_date")
    val close_approach_date: String,
    @Json(name = "relative_velocity")
    val relative_velocity: RelativeVelocity,
    @Json(name = "miss_distance")
    val miss_distance: MissDistance
) : Parcelable

@Parcelize
data class MissDistance(
    @Json(name = "astronomical")
    val astronomical: Double
) : Parcelable

@Parcelize
data class RelativeVelocity(
    @Json(name = "kilometers_per_second")
    val kilometers_per_second: Double
) : Parcelable

@Parcelize
data class EstimatedDiameter(
    @Json(name = "kilometers")
    val kilometers: Feet
) : Parcelable

@Parcelize
data class Feet(
    @Json(name = "estimated_diameter_max")
    val estimated_diameter_max: Double
) : Parcelable


data class AsteroidResponse(
    @Json(name = "links")
    val links: links,
    @Json(name = "element_count")
    val element_count: Long,
    @Json(name = "near_earth_objects")
    val near_earth_objects: Map<String, List<AsteroidRemote>>
)

data class links(
    val next: String,
    val prev: String,
    val self: String
)