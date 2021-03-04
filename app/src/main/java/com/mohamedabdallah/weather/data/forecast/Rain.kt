package com.mohamedabdallah.weather.data.forecast

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data
class Rain(
        @SerializedName("3h")
        var mH: Double? = null
):Serializable {
}