package com.mohamedabdallah.weather.data.weather

import com.google.gson.annotations.SerializedName

data
class Sys(
        var country: String? = null,
        var id: Long? = null,
        var sunrise: Long,
        var sunset: Long,
        var type: Long? = null
) {
}

 