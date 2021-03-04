package com.mohamedabdallah.weather.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.mohamedabdallah.weather.data.forecast.ForecastData
import com.mohamedabdallah.weather.data.model.FavoritePlace
import com.mohamedabdallah.weather.data.weather.WeatherData

class LocalSource (application:Application) {


    private val database: WeatherDatabase = WeatherDatabase.getInstance(application)

    fun getFavoritesPlaces():LiveData<List<FavoritePlace>>{
        return database.dao.getFavoritesPlaces()
    }
    suspend fun addFavoritePlace(favoritePlace: FavoritePlace){
        database.dao.addFavoritePlace(favoritePlace)
    }
    suspend fun deleteFavoritePlace(favoritePlace: FavoritePlace){
        database.dao.deleteFavoritePlace(favoritePlace)
    }

    /*
    fun getWeatherByCityName():LiveData<WeatherData>{
        return database.dao.getWeatherByCityName()
    }

    fun getWeatherByLatLong():LiveData<WeatherData>{
        return database.dao.getWeatherByLatLong()
    }

    fun getWeatherForecastByCityName():LiveData<ForecastData>{
         return database.dao.getWeatherForecastByCityName()
    }
    fun getWeatherForecastByLatLong():LiveData<ForecastData>{
        return database.dao.getWeatherForecastByLatLong()
    }


    suspend fun insertWeatherData(weatherData: WeatherData){
        database.dao.insertWeatherData(weatherData)
    }

    suspend fun insertForecastData(forecastData:ForecastData){
        database.dao.insertForecastData(forecastData)
    }

     */

}