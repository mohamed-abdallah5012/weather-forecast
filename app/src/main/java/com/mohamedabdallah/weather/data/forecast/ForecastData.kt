package com.mohamedabdallah.weather.data.forecast

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity
data
class ForecastData(

        @PrimaryKey(autoGenerate = true)
        val idForecastData:Int,
        @SerializedName("city")
        var mCity: City? = null,
        @SerializedName("cnt")
        val mCnt: Long? = null,

        @SerializedName("cod")
        val mCod: String? = null,

        @Ignore
        @SerializedName("list")
        var mList: List<ListWeatherInfo>,

        @SerializedName("message")
        val mMessage: Long? = null
) {
}