package com.mohamedabdallah.weather.data.weather

import com.google.gson.annotations.SerializedName

data class Coord(
        @SerializedName("lat")
         var mLat: Double? = null,
        @SerializedName("lon")
         val mLon: Double? = null
) {
}