package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.local.getDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

class MainViewModel(app: Application) : ViewModel() {

    private val repository = AsteroidRepository(getDatabase(app))
    val asteroidList = repository.asteroids
    val dailyAsteroids = repository.asteroidsForToday
    val weeklyAsteroids = repository.asteroidForWeek
    val picOfDay = repository.pictureOfDay

    init {
        fetchDataFromRepo()
        getPicOfDay()
    }

    private fun fetchDataFromRepo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.refreshAsteroids()
            } catch (e: IOException) {
                Timber.e(e)
            }
        }
    }

    private fun getPicOfDay() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getPicOfDay()
            } catch (e: IOException) {
                Timber.e(e)
            }
        }
    }

}

class Factory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}