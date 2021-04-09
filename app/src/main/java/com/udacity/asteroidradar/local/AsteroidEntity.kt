package com.udacity.asteroidradar.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asteroid_table")
data class AsteroidEntity(

    @PrimaryKey val id: Long, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

@Entity(tableName = "pic_of_day")
data class PictureOfDayEntity(
    @PrimaryKey
    val date: String,
    val media_type: String?,
    val title: String,
    val url: String
)