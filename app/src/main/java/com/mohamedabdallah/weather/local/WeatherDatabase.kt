package com.mohamedabdallah.weather.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mohamedabdallah.weather.data.alert.AlertResponse
import com.mohamedabdallah.weather.data.favorite.FavoritePlace
import com.mohamedabdallah.weather.data.forecast.ForecastResponse
import com.mohamedabdallah.weather.data.weather.CurrentResponse

@Database(entities = [FavoritePlace::class,
                        ForecastResponse::class,
                            CurrentResponse::class,
                                AlertResponse::class],
                                    version = 1,exportSchema = false)
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


