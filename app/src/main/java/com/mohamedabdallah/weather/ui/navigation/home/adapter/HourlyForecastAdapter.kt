package com.mohamedabdallah.weather.ui.navigation.home.adapter

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.data.forecast.ForecastCustomizedModel
import com.mohamedabdallah.weather.data.model.FavoritePlace
import com.mohamedabdallah.weather.ui.activity.MainActivity

class HourlyForecastAdapter(
        private var hourlyForecastList: List<ForecastCustomizedModel>
) :
        RecyclerView.Adapter<HourlyForecastAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO bind item
        val current:ForecastCustomizedModel=hourlyForecastList[position]
        holder.date.text=current.dayOfTheWeek+current.dateOfTheMonth+current.monthOfTheYear
        holder.temp.text=current.temperature
        holder.time.text=current.time
        // image
        when(current.weatherType){

            holder.itemView.resources.getString(R.string.clear) ->{
                holder.cardView.setCardBackgroundColor(R.color.lightYellow)
                holder.image.setImageResource(R.drawable.ic_sunny)

            }
            holder.itemView.resources.getString(R.string.thunderstorm) ->{
                holder.cardView.setCardBackgroundColor(R.color.darkSlateBlue)
                holder.image.setImageResource(R.drawable.ic_thunderstorm)

            }
            holder.itemView.resources.getString(R.string.clouds) -> {
                holder.cardView.setCardBackgroundColor(R.color.lightBlue)
                holder.image.setImageResource(R.drawable.ic_cloudy)

            }
            holder.itemView.resources.getString(R.string.rain) ->{
                holder.cardView.setCardBackgroundColor(R.color.lightSlateGray)
                holder.image.setImageResource(R.drawable.ic_rain)

            }



            else -> {
                holder.cardView.setCardBackgroundColor(R.color.indianRed)
                //iconDrawable.set(getWeatherIcon(context,""))
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

    fun setData(list: List<ForecastCustomizedModel>) {
        hourlyForecastList = list
        notifyDataSetChanged()

    }

    fun getData() : List<ForecastCustomizedModel>{
        return hourlyForecastList
    }
}
