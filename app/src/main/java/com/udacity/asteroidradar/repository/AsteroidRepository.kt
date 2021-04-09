package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.DateUtil
import com.udacity.asteroidradar.api.AsteroidNetwork
import com.udacity.asteroidradar.asPicEntity
import com.udacity.asteroidradar.local.AsteroidDB
import com.udacity.asteroidradar.local.AsteroidEntity
import com.udacity.asteroidradar.local.PictureOfDayEntity
import com.udacity.asteroidradar.parseAsteroidResponseToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class AsteroidRepository(private val database: AsteroidDB) {

    val asteroids: LiveData<List<AsteroidEntity>> = database.asteroidDao.getAsteroidsFromLocal()
    val asteroidsForToday: LiveData<List<AsteroidEntity>> =
        database.asteroidDao.getAsteroidsForToday(DateUtil.currentDate())
    val pictureOfDay: LiveData<PictureOfDayEntity> =
        database.asteroidDao.getPictureOfDay(DateUtil.currentDate())
    val asteroidForWeek: LiveData<List<AsteroidEntity>> =
        database.asteroidDao.getAsteroidsByDate(DateUtil.currentDate(), DateUtil.currentDate(7))

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val remoteResponse = AsteroidNetwork.asteroidApi.getAsteroids(
                startDate = DateUtil.currentDate(),
                endDate = DateUtil.currentDate(7),
                apiKey = API_KEY
            )
            database.asteroidDao.saveAsteroids(parseAsteroidResponseToList(remoteResponse.near_earth_objects))
        }
    }

    suspend fun getPicOfDay() {
        withContext(Dispatchers.IO) {
            val picResponse = AsteroidNetwork.asteroidApi.getPictureOfTheDay(API_KEY)
            database.asteroidDao.insertPicture(picResponse.asPicEntity())
            Timber.d(picResponse.toString())
        }
    }
}