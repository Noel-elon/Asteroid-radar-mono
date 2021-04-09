package com.udacity.asteroidradar.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    @Query("select * from asteroid_table")
    fun getAsteroidsFromLocal(): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAsteroids(asteroids: List<AsteroidEntity>)

    @Query("SELECT * From asteroid_table where closeApproachDate = :currentDate ")
    fun getAsteroidsForToday(currentDate: String): LiveData<List<AsteroidEntity>>


    @Query("SELECT * From asteroid_table where closeApproachDate between :startDate And :endDate Order By closeApproachDate ASC")
    fun getAsteroidsByDate(
        startDate: String,
        endDate: String
    ): LiveData<List<AsteroidEntity>>

    @Query("SELECT * From pic_of_day where date = :currentDate")
    fun getPictureOfDay(currentDate: String): LiveData<PictureOfDayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPicture(picture: PictureOfDayEntity)

}