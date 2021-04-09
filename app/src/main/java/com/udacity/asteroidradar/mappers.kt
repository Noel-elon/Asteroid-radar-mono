package com.udacity.asteroidradar

import com.udacity.asteroidradar.api.AsteroidRemote
import com.udacity.asteroidradar.local.AsteroidEntity
import com.udacity.asteroidradar.local.PictureOfDayEntity

fun List<AsteroidEntity>.asDomainEntity(): List<Asteroid> =
    map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }

fun List<AsteroidRemote>.asLocalEntity(): List<AsteroidEntity> =
    map {
        AsteroidEntity(
            id = it.id,
            codename = it.name,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absolute_magnitude_h,
            estimatedDiameter = it.estimated_diameter.kilometers.estimated_diameter_max,
            relativeVelocity = it.close_approach_data[0].relative_velocity.kilometers_per_second,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.is_potentially_hazardous_asteroid
        )
    }

fun PictureOfDay.asPicEntity(): PictureOfDayEntity =
    PictureOfDayEntity(
        date = date,
        media_type = mediaType,
        title = title,
        url = url
    )

fun PictureOfDayEntity.asPicDomain(): PictureOfDay =
    PictureOfDay(
        date = date,
        mediaType = media_type,
        title = title,
        url = url

    )