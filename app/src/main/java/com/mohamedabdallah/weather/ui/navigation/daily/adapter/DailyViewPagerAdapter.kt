package com.mohamedabdallah.weather.ui.navigation.daily.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.data.forecast.ForecastCustomizedModel
import com.mohamedabdallah.weather.data.model.FavoritePlace

class DailyViewPagerAdapter(
        private var list: List<FavoritePlace>
) :
        RecyclerView.Adapter<DailyViewPagerAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO bind item


        val current:FavoritePlace=list[position]
        holder.headerDate.text=current.address
        holder.temperature.text=current.name
        holder.degreeSymbol.text=current.id
        holder.mainDesc.text=current.lat.toString()
        holder.fullDesc.text=current.lng.toString()
        holder.humidity.text="humidity"
        holder.pressure.text="pressure"
        holder.visibility.text="visibility"
        holder.cloudiness.text="cloudiness"
        holder.sunset.text="sunset"
        holder.sunrise.text="sunrise"

        holder.weatherIcon.setImageResource(R.drawable.ic_sunny)
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

    fun setData(list: List<FavoritePlace>) {
        this.list = list
        notifyDataSetChanged()

    }

    fun getData() : List<FavoritePlace>{
        return list
    }

}
