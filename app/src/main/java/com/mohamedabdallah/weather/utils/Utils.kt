package com.mohamedabdallah.weather.utils

import android.R
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun getDate(date: String): StringBuilder {

    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date)
    val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    cal.time = date
    val year = cal[Calendar.YEAR]
    val month = cal[Calendar.MONTH] - 1
    val day = cal[Calendar.DAY_OF_MONTH]
    return StringBuilder().append(year).append(" ").append(month).append(" ").append(day)
}
fun getDayOfTheWeek(date: String): String {
    // MON
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date)
    var simpleDateFormat = SimpleDateFormat("EEE")
    return simpleDateFormat.format(date).toUpperCase()
}

fun getMonthOfTheYear(date: String): String {
    // JUNE
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date)
    var simpleDateFormat = SimpleDateFormat("MMMM")
    return simpleDateFormat.format(date).toUpperCase()
}

fun getDateOfTheMonth(date: String): Int {
    // 23
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date)
    val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    cal.time = date
    return cal[Calendar.DAY_OF_MONTH]
}

fun getTime(date: String): String {
    val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val outputFormat: DateFormat =
        SimpleDateFormat("hh:mm a")
    return outputFormat.format(inputFormat.parse(date))

}
fun getSunSet(value:Long):String{

    val sunsetDate = Date(value * 1000L)
    val simpleFormat = SimpleDateFormat("hh:mm a")
    return simpleFormat.format(sunsetDate.time)
}
fun getDate(value:Long):String{

    val a = Date(value * 1000L)
    val simpleFormat = SimpleDateFormat("yyyy-MM-dd")
    return DateFormat.getDateInstance(DateFormat.DEFAULT).format(a.time)
}
fun getCurrentDate():String
{
    val c: Calendar = Calendar.getInstance()
    return  DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.time)
}

fun getCurrentTime():String{
    val c: Calendar = Calendar.getInstance()
    val simpleTimeFormat = SimpleDateFormat("hh:mm")
    return  simpleTimeFormat.format(c.time)
}

fun saveToInternalStorage(bitmapImage: Bitmap,context: Context,name:String): String? {

    val cw = ContextWrapper(context)
    val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
    val mypath = File(directory, "$name.jpg")
    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(mypath)
        bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return directory.getAbsolutePath()
}

fun loadImageFromStorage(path: String,name:String):Bitmap {

        val f = File(path, name)
        val b = BitmapFactory.decodeStream(FileInputStream(f))
       return b
    }

