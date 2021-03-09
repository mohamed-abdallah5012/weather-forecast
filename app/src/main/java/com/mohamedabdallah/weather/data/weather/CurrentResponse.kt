package com.mohamedabdallah.weather.data.weather

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data
class CurrentResponse(

        @PrimaryKey
        var currentResponseId:String,

        var base: String? = null,
        var clouds: Clouds? = null,
        var cod: Long? = null,
        var dt: Long? = null,
        var id: Long? = null,
        var main: Main? = null,
        var name: String? = null,
        var sys: Sys? = null,
        var timezone: Long? = null,
        var visibility: Long? = null,
        var weather: List<Weather>,
        var wind: Wind? = null,
        @Embedded
        val coord: Coord

) {
}