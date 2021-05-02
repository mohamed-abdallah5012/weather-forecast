package com.mohamedabdallah.weather.remote



import com.mohamedabdallah.weather.data.weather.CurrentResponse
import com.mohamedabdallah.weather.data.forecast.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APiInterface {


    @GET("weather")
    suspend fun getWeatherByLatLong(@Query("lat") latitude:Double,
                                        @Query("lon") longitude :Double,
                                            @Query("appid") appId:String,
                                                @Query("lang") lang:String,
                                                   @Query("units") unit: String):Response<CurrentResponse>

    @GET("onecall")
    suspend fun getForecastOneApi(@Query("lat") latitude:Double,
                                    @Query("lon") longitude :Double,
                                        @Query("appid") appId:String,
                                            @Query("lang") lang:String,
                                                @Query("exclude") exclude: String,
                                                    @Query("units") unit: String):Response<ForecastResponse>

    /*
    @GET("forecast")
    suspend fun getWeatherForecastByCityName(@Query("q") cityName:String,
                                                @Query("appid") appId:String,
                                                   @Query("lang") lang:String,
                                                      @Query("units") unit: String):Response<ForecastData>

    @GET("forecast")
    suspend fun getWeatherForecastByLatLong(@Query("lat") latitude:String,
                                                @Query("lon") longitude :String,
                                                    @Query("appid") appId:String,
                                                         @Query("lang") lang:String,
                                                                @Query("units") unit: String):Response<ForecastData>

      */

    /*
    @GET("weather")
    suspend fun getWeatherByCityName(@Query("q") cityName:String,
                                       @Query("appid") appId:String,
                                           @Query("lang") lang:String,
                                             @Query("units") unit: String):Response<CurrentResponse>

   */



}