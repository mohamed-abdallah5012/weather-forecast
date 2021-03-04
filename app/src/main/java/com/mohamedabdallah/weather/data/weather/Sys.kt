package com.mohamedabdallah.weather.data.weather

import com.google.gson.annotations.SerializedName

data
class Sys(
        @SerializedName("country")
        var mCountry: String? = null,
        @SerializedName("id")
        val mId: Long? = null,

        @SerializedName("sunrise")
        val mSunrise: Long,

        @SerializedName("sunset")
        val mSunset: Long,

        @SerializedName("type")
         val mType: Long? = null
) {
}

 