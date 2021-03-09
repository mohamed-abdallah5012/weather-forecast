package com.mohamedabdallah.weather.data.forecast

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.microseconds

@Entity
data class ForecastResponse(

    @PrimaryKey
    var forecastResponseId:String,

    val alerts: List<Alert>?=null,
    val daily: List<Daily>,
    val hourly: List<Hourly>,

    var lat: Double,
    var lon: Double,
    val timezone: String,
    val timezone_offset: Double


){

}