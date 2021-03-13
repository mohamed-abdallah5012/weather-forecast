package com.mohamedabdallah.weather.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mohamedabdallah.weather.data.weather.*
import com.mohamedabdallah.weather.data.forecast.Alert
import com.mohamedabdallah.weather.data.forecast.Daily
import com.mohamedabdallah.weather.data.forecast.Hourly
import java.util.*


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
    fun toWeather(value: String): List<Weather> {
        val listType = object : TypeToken<List<Weather>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromWeather(list: List<Weather>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toAlerts(value: String?): List<Alert?>? {
        val listType = object : TypeToken<List<Alert?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromAlerts(list: List<Alert>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toDaily(value: String): List<Daily> {
        val listType = object : TypeToken<List<Daily>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromDaily(list: List<Daily>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toHourly(value: String): List<Hourly> {
        val listType = object : TypeToken<List<Hourly>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromHourly(list: List<Hourly>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

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
    fun fromUUID(list: List<UUID>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toUUID(value: String): List<UUID> {
        val listType = object : TypeToken<List<UUID>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromSelectedDays(list: List<Int>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toSelectedDays(value: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }


    @TypeConverter
    fun fromAlert(alert: Alert): String
    {
        return Gson().toJson(alert)
    }

    @TypeConverter
    fun toAlert(value :String) : Alert

    {
        return Gson().fromJson(value,Alert::class.java)
    }
    @TypeConverter
    fun fromHourly(hourly: Hourly): String
    {
        return Gson().toJson(hourly)
    }

    @TypeConverter
    fun toHourly1(value :String) : Hourly

    {
        return Gson().fromJson(value,Hourly::class.java)
    }
}