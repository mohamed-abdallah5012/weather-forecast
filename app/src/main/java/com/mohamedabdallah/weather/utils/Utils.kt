package com.mohamedabdallah.weather.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.mohamedabdallah.weather.R
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

