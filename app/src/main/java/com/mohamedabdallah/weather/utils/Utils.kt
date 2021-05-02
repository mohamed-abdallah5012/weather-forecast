package com.mohamedabdallah.weather.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.view.animation.Animation
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
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

fun getLiveWeekDays():LinkedHashMap<Int, Boolean>
{
    val calendar = Calendar.getInstance()
    var map: LinkedHashMap<Int, Boolean> = LinkedHashMap()


    when (calendar[Calendar.DAY_OF_WEEK]) {
        Calendar.SUNDAY -> {
            map[Calendar.MONDAY] = true
            map[Calendar.TUESDAY] = false
            map[Calendar.WEDNESDAY] = false
            map[Calendar.THURSDAY] = false
            map[Calendar.FRIDAY] = false
            map[Calendar.SATURDAY] = false
            map[Calendar.SUNDAY] = false
        }
        Calendar.MONDAY -> {
            map[Calendar.TUESDAY] = true
            map[Calendar.WEDNESDAY] = false
            map[Calendar.THURSDAY] = false
            map[Calendar.FRIDAY] = false
            map[Calendar.SATURDAY] = false
            map[Calendar.SUNDAY] = false
            map[Calendar.MONDAY] = false
        }
        Calendar.TUESDAY -> {
            map[Calendar.WEDNESDAY] = true
            map[Calendar.THURSDAY] = false
            map[Calendar.FRIDAY] = false
            map[Calendar.SATURDAY] = false
            map[Calendar.SUNDAY] = false
            map[Calendar.MONDAY] = false
            map[Calendar.TUESDAY] = false

        }
        Calendar.WEDNESDAY -> {
            map[Calendar.THURSDAY] = true
            map[Calendar.FRIDAY] = false
            map[Calendar.SATURDAY] = false
            map[Calendar.SUNDAY] = false
            map[Calendar.MONDAY] = false
            map[Calendar.TUESDAY] = false
            map[Calendar.WEDNESDAY] = false

        }
        Calendar.THURSDAY -> {
            map[Calendar.FRIDAY] = true
            map[Calendar.SATURDAY] = false
            map[Calendar.SUNDAY] = false
            map[Calendar.MONDAY] = false
            map[Calendar.TUESDAY] = false
            map[Calendar.WEDNESDAY] = false
            map[Calendar.THURSDAY] = false
        }
        Calendar.FRIDAY -> {
            map[Calendar.SATURDAY] = true
            map[Calendar.SUNDAY] = false
            map[Calendar.MONDAY] = false
            map[Calendar.TUESDAY] = false
            map[Calendar.WEDNESDAY] = false
            map[Calendar.THURSDAY] = false
            map[Calendar.FRIDAY] = false


        }
        Calendar.SATURDAY -> {
            map[Calendar.SUNDAY] = true
            map[Calendar.MONDAY] = false
            map[Calendar.TUESDAY] = false
            map[Calendar.WEDNESDAY] = false
            map[Calendar.THURSDAY] = false
            map[Calendar.FRIDAY] = false
            map[Calendar.SATURDAY] = false

        }
    }
return map
}

fun calcTTTTTTTTTTTTTTTTTTTTTTT(incoming :List<Int>): List<Int> {

    var result= incoming.toMutableList()
    val calendar = Calendar.getInstance()
    val today:Int=calendar[Calendar.DAY_OF_WEEK]
    for (i in incoming.indices)
    {
        when {
            today ==incoming[i] -> {
                result[i]=7
            }
            today <incoming[i] -> {
               result[i]=incoming[i]-today
            }
            today >incoming[i] -> {
                result[i]=incoming[i]+7-today
            }
        }
    }
    return result

}

fun View.startAnimation(animation: Animation, onEnd: () -> Unit) {
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) = Unit
        override fun onAnimationEnd(animation: Animation?) {
            onEnd()
        }
        override fun onAnimationRepeat(animation: Animation?) = Unit
    })
    this.startAnimation(animation)
}



