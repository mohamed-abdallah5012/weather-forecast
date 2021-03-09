package com.mohamedabdallah.weather.remote

import com.mohamedabdallah.weather.data.weather.CurrentResponse
import com.mohamedabdallah.weather.data.forecast.ForecastResponse
import com.mohamedabdallah.weather.utils.Constant
import retrofit2.Response

class RemoteSource {

    private val aPiInterface: APiInterface = ApiClient.getInstance()
            .create(APiInterface::class.java)


   /* suspend fun getWeatherByCityName(cityName:String,appId:String,lang:String,unit: String):
            Response<CurrentResponse> {
       return aPiInterface.getWeatherByCityName(cityName,appId,lang,unit)
    }

    */

    suspend fun getWeatherByLatLong(latitude:Double,longitude :Double, appId:String, lang:String, unit: String):
            Response<CurrentResponse> {
        return aPiInterface.getWeatherByLatLong(latitude,longitude,appId,lang,unit)
    }

    suspend fun getForecastOneApi(latitude:Double,longitude :Double, appId:String, lang:String, unit: String):
            Response<ForecastResponse> {
        return aPiInterface.getForecastOneApi(latitude,longitude,appId,lang,Constant.exclude,unit)
    }



}