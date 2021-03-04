package com.mohamedabdallah.weather.data.forecast

import com.google.gson.annotations.SerializedName
import com.mohamedabdallah.weather.data.weather.Clouds
import com.mohamedabdallah.weather.data.weather.Main
import com.mohamedabdallah.weather.data.weather.Weather
import com.mohamedabdallah.weather.data.weather.Wind
import java.io.Serializable

data
class ListWeatherInfo(

        @SerializedName("clouds")
        var mClouds: Clouds? = null,
        @SerializedName("dt")
        val mDt: Long? = null,

        @SerializedName("dt_txt")
        val mDtTxt: String? = null,

        @SerializedName("main")
        val mMain: Main? = null,

        @SerializedName("rain")
        val mRain: Rain? = null,

        @SerializedName("sys")
        val mSys: Sys? = null,

        @SerializedName("weather")
        val mWeather: List<Weather>? = null,

        @SerializedName("wind")
        val mWind: Wind? = null
):Serializable {
}