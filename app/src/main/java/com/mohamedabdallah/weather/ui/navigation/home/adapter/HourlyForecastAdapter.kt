package com.mohamedabdallah.weather.ui.navigation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.data.forecast.Hourly
import com.mohamedabdallah.weather.utils.getDate
import com.mohamedabdallah.weather.utils.getSunSet

class HourlyForecastAdapter(
        private var hourlyForecastList: List<Hourly>
) :
        RecyclerView.Adapter<HourlyForecastAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO bind item
        val current: Hourly =hourlyForecastList[position]
        holder.date.text= getDate(current.dt)
        holder.temp.text=current.temp.toString()
        holder.time.text= getSunSet(current.dt)
        // image
         when(current.weather[0].main){

            holder.itemView.resources.getString(R.string.clouds) -> {
                holder.cardView.setCardBackgroundColor(R.color.indianRed)
                holder.image.setImageResource(R.drawable.ic_cloudy)

            }
            holder.itemView.resources.getString(R.string.rain) ->{
                holder.cardView.setCardBackgroundColor(R.color.indianRed)
                holder.image.setImageResource(R.drawable.ic_rain)

            }
            holder.itemView.resources.getString(R.string.clear) ->{
                holder.cardView.setCardBackgroundColor(R.color.indianRed)
                holder.image.setImageResource(R.drawable.ic_sunny)

            }
            holder.itemView.resources.getString(R.string.thunderstorm) ->{
                holder.cardView.setCardBackgroundColor(R.color.indianRed)
                holder.image.setImageResource(R.drawable.ic_thunderstorm)

            }
            else -> {
                //holder.weatherIcon.setImageResource(R.drawable.ic_thunderstorm)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // TODO createView
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.hour_layout1
                , parent, false)
        return ViewHolder(view)
    }

     class ViewHolder(view: View) :
            RecyclerView.ViewHolder(view){
         var date: TextView = view.findViewById(R.id.hourly_date)
         var time: TextView = view.findViewById(R.id.hourly_time)
         var image: ImageView = view.findViewById(R.id.hourly_image)
         var temp: TextView = view.findViewById(R.id.hourly_temp)
         var cardView:CardView=view.findViewById(R.id.card_hour)
    }

    override fun getItemCount(): Int {
        return hourlyForecastList.size
    }

    fun setData(list: List<Hourly>) {
        hourlyForecastList = list
        notifyDataSetChanged()

    }

    fun getData() : List<Hourly>{
        return hourlyForecastList
    }
}
