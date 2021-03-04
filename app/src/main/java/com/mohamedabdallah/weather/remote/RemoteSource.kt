package com.mohamedabdallah.weather.remote

import com.mohamedabdallah.weather.data.forecast.ForecastData
import com.mohamedabdallah.weather.data.weather.WeatherData
import retrofit2.Response
import retrofit2.http.Query

class RemoteSource {

    private val aPiInterface: APiInterface = ApiClient.getInstance()
            .create(APiInterface::class.java)


    suspend fun getWeatherByCityName(cityName:String,appId:String,lang:String,unit: String):
            Response<WeatherData> {
       return aPiInterface.getWeatherByCityName(cityName,appId,lang,unit)
    }

    suspend fun getWeatherByLatLong(latitude:String,longitude :String, appId:String, lang:String, unit: String):
            Response<WeatherData> {
        return aPiInterface.getWeatherByLatLong(latitude,longitude,appId,lang,unit)
    }

    suspend fun getWeatherForecastByCityName(cityName:String,appId:String,lang:String,unit: String):
    Response<ForecastData> {
        return aPiInterface.getWeatherForecastByCityName(cityName,appId,lang,unit)
    }

    suspend fun getWeatherForecastByLatLong(latitude:String,longitude :String, appId:String, lang:String, unit: String):
            Response<ForecastData> {
        return aPiInterface.getWeatherForecastByLatLong(latitude,longitude,appId,lang,unit)
    }



}