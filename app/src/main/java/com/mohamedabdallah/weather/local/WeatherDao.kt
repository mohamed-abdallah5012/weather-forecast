package com.mohamedabdallah.weather.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mohamedabdallah.weather.data.forecast.ForecastData
import com.mohamedabdallah.weather.data.model.FavoritePlace
import com.mohamedabdallah.weather.data.weather.WeatherData

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoritePlace(item: FavoritePlace)

    @Delete
    suspend fun deleteFavoritePlace(item: FavoritePlace)

    @Query("select * from FavoritePlace ")
    fun getFavoritesPlaces(): LiveData<List<FavoritePlace>>

    /*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherData: WeatherData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecastData(forecastData: ForecastData)



    @Query("select * from WeatherData ")
    fun getWeatherByCityName(): LiveData<WeatherData>

    @Query("select * from  WeatherData")
    fun getWeatherByLatLong(): LiveData<WeatherData>


    @Query("select * from ForecastData ")
    fun getWeatherForecastByCityName(): LiveData<ForecastData>


    @Query("select * from ForecastData ")
    fun getWeatherForecastByLatLong(): LiveData<ForecastData>


     */
}