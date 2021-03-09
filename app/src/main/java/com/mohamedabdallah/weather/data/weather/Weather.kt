package com.mohamedabdallah.weather.data.weather

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data
class Weather(
        var description: String?=null,
        var icon: String?=null,
        var id: Long?=null,
        var main: String?=null
)
