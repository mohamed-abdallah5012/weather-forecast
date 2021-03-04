package com.mohamedabdallah.weather.data.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data
class WeatherData(
        @PrimaryKey(autoGenerate = true)
        val weatherDataID:Int,
        @SerializedName("base")
        var mBase: String? = null,
        @SerializedName("clouds")
        val mClouds: Clouds? = null,

        @SerializedName("cod")
        val mCod: Long? = null,

        @SerializedName("coord")
        val mCoord: Coord? = null,

        @SerializedName("dt")
        val mDt: Long? = null,

        @SerializedName("id")
        val mId: Long? = null,

        @SerializedName("main")
        val mMain: Main? = null,

        @SerializedName("name")
        val mName: String? = null,

        @SerializedName("sys")
        val mSys: Sys? = null,

        @SerializedName("timezone")
        val mTimezone: Long? = null,

        @SerializedName("visibility")
        val mVisibility: Long? = null,

        @SerializedName("weather")
        val mWeather: List<Weather>,

        @SerializedName("wind")
         val mWind: Wind? = null
) {
}