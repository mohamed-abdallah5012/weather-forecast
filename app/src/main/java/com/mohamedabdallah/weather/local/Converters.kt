package com.mohamedabdallah.weather.local

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mohamedabdallah.weather.data.forecast.City
import com.mohamedabdallah.weather.data.forecast.ListWeatherInfo
import com.mohamedabdallah.weather.data.weather.*
import java.io.ByteArrayOutputStream

class Converters {

    @TypeConverter
    fun fromClouds(clouds: Clouds): String
    {
        return Gson().toJson(clouds)
    }

    @TypeConverter
    fun toClouds(value :String) :Clouds

    {
        return Gson().fromJson(value,Clouds::class.java)

    }


    //

    @TypeConverter
    fun fromCoord(coord: Coord): String
    {
        return Gson().toJson(coord)
    }

    @TypeConverter
    fun toCoord(value :String) :Coord

    {
        return Gson().fromJson(value,Coord::class.java)
    }

    //

    @TypeConverter
    fun fromMain(main: Main): String
    {
        return Gson().toJson(main)
    }

    @TypeConverter
    fun toMain(value :String) : Main

    {
        return Gson().fromJson(value,Main::class.java)
    }

    //

    @TypeConverter
    fun fromSys(sys: Sys): String
    {
        return Gson().toJson(sys)
    }

    @TypeConverter
    fun toSys(value :String) : Sys

    {
        return Gson().fromJson(value,Sys::class.java)
    }

    //

    @TypeConverter
    fun fromWeather(weather: Weather): String
    {
        return Gson().toJson(weather)
    }

    @TypeConverter
    fun toWeather(value :String) : Weather

    {
        return Gson().fromJson(value,Weather::class.java)
    }
    //

    @TypeConverter
    fun fromWind(wind: Wind): String
    {
        return Gson().toJson(wind)
    }

    @TypeConverter
    fun toWind(value :String) : Wind

    {
        return Gson().fromJson(value,Wind::class.java)
    }

    @TypeConverter
    fun fromCity(city: City): String
    {
        return Gson().toJson(city)
    }

    @TypeConverter
    fun toCity(value :String) : City

    {
        return Gson().fromJson(value,City::class.java)
    }

    @TypeConverter
    fun fromListWeatherInfo(list: ListWeatherInfo): String
    {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toListWeatherInfo(value :String) : ListWeatherInfo

    {
        return Gson().fromJson(value,ListWeatherInfo::class.java)
    }

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray
    {
        val outputStream=ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(value :ByteArray) : Bitmap

    {
        return BitmapFactory.decodeByteArray(value,0,value.size)
    }




}