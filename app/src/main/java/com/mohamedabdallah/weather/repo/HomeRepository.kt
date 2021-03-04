package com.mohamedabdallah.weather.repo


import com.mohamedabdallah.weather.local.LocalSource
import com.mohamedabdallah.weather.remote.RemoteSource
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohamedabdallah.weather.data.forecast.ForecastData
import com.mohamedabdallah.weather.data.model.FavoritePlace
import com.mohamedabdallah.weather.data.weather.WeatherData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeRepository(application: Application) {


    private val remote = RemoteSource()
    private val local = LocalSource(application)

    private val job= Job()
    private val uiScope= CoroutineScope(Dispatchers.Main +job)

    private var weatherData =MutableLiveData<WeatherData>()
    private var forecastData =MutableLiveData<ForecastData>()

    fun getWeatherByCityName(cityName:String,appId:String,lang:String,unit: String): LiveData<WeatherData> {
        uiScope.launch(Dispatchers.Main) {
            val response=remote.getWeatherByCityName(cityName,appId,lang,unit)
            if (response.isSuccessful)
            {
                weatherData.value=response.body()

                //response.body()?.let { local.insertWeatherData(it) }
            } else {
                Log.i("TAG", "Get Message: ${response.message()}")
            }

        }
        //
        //return local.getWeatherByCityName()
        return weatherData

    }
    fun getWeatherByLatLong(latitude:String,longitude :String, appId:String, lang:String, unit: String): LiveData<WeatherData> {
        uiScope.launch(Dispatchers.Main) {
            val response=remote.getWeatherByLatLong(latitude,longitude,appId,lang,unit)
            if (response.isSuccessful)
            {
                weatherData.value=response.body()
               // response.body()?.let { local.insertWeatherData(it) }
            } else {
                Log.i("TAG", "Get Message: ${response.message()}")
            }

        }
        //return local.getWeatherByLatLong()
        return weatherData

    }
    fun getWeatherForecastByCityName(cityName:String,appId:String,lang:String,unit: String):
            LiveData<ForecastData> {
        uiScope.launch(Dispatchers.Main) {
            val response=remote.getWeatherForecastByCityName(cityName, appId, lang, unit)
            if (response.isSuccessful)
            {
                forecastData.value=response.body()
                //response.body()?.let { local.insertForecastData(it) }
            } else {
                Log.i("TAG", "Get Message: ${response.message()}")
            }

        }
        return forecastData
        //return local.getWeatherForecastByCityName()

    }
    fun getWeatherForecastByLatLong(latitude:String,longitude :String, appId:String, lang:String, unit: String):
            LiveData<ForecastData> {
        uiScope.launch(Dispatchers.Main) {
            val response=remote.getWeatherForecastByLatLong(latitude, longitude, appId, lang, unit)
            if (response.isSuccessful)
            {
                forecastData.value=response.body()
               // response.body()?.let { local.insertForecastData(it) }
            } else {
                Log.i("TAG", "Get Message: ${response.message()}")
            }

        }
        return forecastData
        //return local.getWeatherForecastByLatLong()

    }

    fun addFavoritePlace(favoritePlace: FavoritePlace) {
        uiScope.launch {
            local.addFavoritePlace(favoritePlace)
        }
    }
    fun deleteFavoritePlace(favoritePlace: FavoritePlace) {
        uiScope.launch {
            local.deleteFavoritePlace(favoritePlace)
        }
    }
    fun getFavoritesPlaces():LiveData<List<FavoritePlace>> {
       return local.getFavoritesPlaces()
    }




}