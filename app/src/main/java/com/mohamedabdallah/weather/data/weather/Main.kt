package com.mohamedabdallah.weather.data.weather

import com.google.gson.annotations.SerializedName

data
class Main(
        var feels_like: Double? = null,
        var humidity: Long? = null,
        var pressure: Long? = null,
        var temp: Float? = null,
        var temp_max: Double? = null,
        var temp_min: Double? = null
) {
}