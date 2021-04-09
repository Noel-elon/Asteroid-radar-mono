package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface AsteroidApi {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(@Query("start_date") startDate: String,
                             @Query("end_date") endDate: String,
                             @Query("api_key") apiKey: String) : AsteroidResponse

    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(@Query("api_key") apiKey: String): PictureOfDay
}

object AsteroidNetwork {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
       .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val asteroidApi: AsteroidApi = retrofit.create(AsteroidApi::class.java)
}