package com.mohamedabdallah.weather.ui.navigation.daily.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.data.forecast.Daily
import com.mohamedabdallah.weather.utils.Constant
import com.mohamedabdallah.weather.utils.getDate
import com.mohamedabdallah.weather.utils.getSunSet

class DailyViewPagerAdapter(
        private var list: List<Daily>
) :
        RecyclerView.Adapter<DailyViewPagerAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO bind item


        val current:Daily=list[position]
        holder.headerDate.text= getDate(current.dt)
        holder.temperature.text=current.temp.day.toString()
        var sy="ss"
        sy = if(Constant.baseUnit=="metric")
            "C"
        else "F"
        holder.degreeSymbol.text=sy
        holder.mainDesc.text=current.weather[0].main
        holder.fullDesc.text=current.weather[0].description
        holder.humidity.text=current.humidity.toString() + " %"
        holder.pressure.text=current.pressure.toString()
        holder.visibility.text=current.wind_speed.toString()
        holder.cloudiness.text=current.clouds.toString() + " %"
        holder.sunset.text= getSunSet(current.sunset)
        holder.sunrise.text= getSunSet(current.sunrise)
        when(current.weather[0].main){

            holder.itemView.resources.getString(R.string.clouds) -> {
                holder.weatherIcon.setImageResource(R.drawable.ic_cloudy)

            }
            holder.itemView.resources.getString(R.string.rain) ->{
                holder.weatherIcon.setImageResource(R.drawable.ic_rain)

            }
            holder.itemView.resources.getString(R.string.clear) ->{
                holder.weatherIcon.setImageResource(R.drawable.ic_sunny)

            }
            holder.itemView.resources.getString(R.string.thunderstorm) ->{
                holder.weatherIcon.setImageResource(R.drawable.ic_thunderstorm)

            }
            else -> {
                //holder.weatherIcon.setImageResource(R.drawable.ic_thunderstorm)
            }
        }

        //holder.image.setImageResource(R.drawable.ic_cloudy)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // TODO createView

        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.view_pager_item
                , parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(view: View) :
            RecyclerView.ViewHolder(view) {
        var headerDate: TextView = view.findViewById(R.id.daily_sheet_date)
        var weatherIcon: ImageView = view.findViewById(R.id.weather_icon)
        var temperature: TextView = view.findViewById(R.id.temperature)
        var degreeSymbol: TextView = view.findViewById(R.id.degree_symbol)
        var mainDesc: TextView = view.findViewById(R.id.main_desc)
        var fullDesc: TextView = view.findViewById(R.id.full_desc)
        var humidity: TextView = view.findViewById(R.id.txt_humidity)
        var pressure: TextView = view.findViewById(R.id.txt_pressure)
        var visibility: TextView = view.findViewById(R.id.txt_visibility)
        var cloudiness: TextView = view.findViewById(R.id.txt_Cloudiness)
        var sunset: TextView = view.findViewById(R.id.txt_sunset)
        var sunrise: TextView = view.findViewById(R.id.txt_sunrise)

    }
    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(list: List<Daily>) {
        this.list = list
        notifyDataSetChanged()

    }

    fun getData() : List<Daily>{
        return list
    }

}
