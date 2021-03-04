package com.mohamedabdallah.weather.data.weather

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Clouds(
        @SerializedName("all") 
        var mAll: Long? = null)
    :Serializable{}