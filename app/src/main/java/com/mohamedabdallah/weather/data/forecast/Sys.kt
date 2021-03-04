package com.mohamedabdallah.weather.data.forecast

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data
class Sys(
        @SerializedName("pod")
        var mPod: String? = null
) :Serializable{
}