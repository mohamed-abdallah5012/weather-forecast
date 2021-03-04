package com.mohamedabdallah.weather.data.weather

import com.google.gson.annotations.SerializedName

data
class Wind (
        @SerializedName("speed")
        var mSpeed: Double? = null
){
}