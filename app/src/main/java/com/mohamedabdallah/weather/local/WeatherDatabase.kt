package com.mohamedabdallah.weather.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mohamedabdallah.weather.data.forecast.ForecastData
import com.mohamedabdallah.weather.data.model.FavoritePlace
import com.mohamedabdallah.weather.data.weather.WeatherData

@Database(entities = [FavoritePlace::class], version = 2,exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val dao: WeatherDao

    companion object {
        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(application: Application): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        application,
                        WeatherDatabase::class.java,
                        "weather_db"
                )
                        //.fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        //.addCallback(WordDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}