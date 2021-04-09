package com.udacity.asteroidradar.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AsteroidEntity::class, PictureOfDayEntity::class], version = 3)
abstract class AsteroidDB : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao

}

private lateinit var INSTANCE: AsteroidDB

fun getDatabase(context: Context): AsteroidDB {
    synchronized(AsteroidDB::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidDB::class.java,
                "asteroids"
            ).fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}