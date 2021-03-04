package com.mohamedabdallah.weather.data.weather

import com.google.gson.annotations.SerializedName

data
class Weather(

        @SerializedName("description")
        var mDescription: String?=null,
        @SerializedName("icon")
        val mIcon: String?=null,

        @SerializedName("id")
        val mWeatherId: Long?=null,

        @SerializedName("main")
        val mMain: String?=null
) {
}