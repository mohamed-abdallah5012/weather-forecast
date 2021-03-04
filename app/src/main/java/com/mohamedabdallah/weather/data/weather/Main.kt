package com.mohamedabdallah.weather.data.weather

import com.google.gson.annotations.SerializedName

data
class Main(
        @SerializedName("feels_like")
        var mFeelsLike: Double? = null,
        @SerializedName("humidity")
        val mHumidity: Long? = null,

        @SerializedName("pressure")
        val mPressure: Long? = null,

        @SerializedName("temp")
        val mTemp: Float? = null,

        @SerializedName("temp_max")
        val mTempMax: Double? = null,

        @SerializedName("temp_min")
        val mTempMin: Double? = null
) {
}