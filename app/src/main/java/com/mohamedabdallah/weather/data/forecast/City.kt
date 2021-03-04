package com.mohamedabdallah.weather.data.forecast

import com.google.gson.annotations.SerializedName
import com.mohamedabdallah.weather.data.weather.Coord


data
class City(
        @SerializedName("coord")
        var mCoord: Coord? = null,
        @SerializedName("country")
        val mCountry: String? = null,

        @SerializedName("id")
        val mId: Long? = null,

        @SerializedName("name")
        val mName: String? = null,

        @SerializedName("population")
        val mPopulation: Long? = null,

        @SerializedName("sunrise")
        val mSunrise: Long? = null,

        @SerializedName("sunset")
        val mSunset: Long? = null,

        @SerializedName("timezone")
        val mTimezone: Long? = null
) {
}