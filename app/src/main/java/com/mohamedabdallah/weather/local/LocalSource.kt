package com.mohamedabdallah.weather.local

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.mohamedabdallah.weather.data.alert.AlertResponse
import com.mohamedabdallah.weather.data.favorite.FavoritePlace
import com.mohamedabdallah.weather.data.forecast.ForecastResponse
import com.mohamedabdallah.weather.data.weather.CurrentResponse
import kotlinx.coroutines.runBlocking

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
    suspend fun getForecastResponse(lat:Double,lng:Double):ForecastResponse?{
        Log.i("TAG", "getForecastResponse: from local")
        return database.dao.getForecastResponse(lat,lng)
    }
    suspend fun saveForecastResponse(resp: ForecastResponse){
        database.dao.saveForecastResponse(resp)
    }
    suspend fun deleteForecastResponse(lat:Double,lng:Double){
        database.dao.deleteForecastResponse(lat,lng)
    }

    suspend fun getCurrentResponse(lat:Double,lng:Double):CurrentResponse?{
        return database.dao.getCurrentResponse(lat,lng)
    }
    suspend fun saveCurrentResponse(resp: CurrentResponse){
        database.dao.saveCurrentResponse(resp)
    }
    suspend fun deleteCurrentResponse(lat:Double,lng:Double){
        database.dao.deleteCurrentResponse(lat,lng)
    }

    suspend fun deleteAlertItem(item:Long)
    {
        database.dao.deleteAlertItem(item)
    }
    fun addAlertList(alert: AlertResponse):Long
    {
        return database.dao.addAlertList(alert)
    }
    fun getAlertList(): LiveData<List<AlertResponse>> {
        return database.dao.getAlertList()
    }


}