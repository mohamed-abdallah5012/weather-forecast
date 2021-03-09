package com.mohamedabdallah.weather.ui.navigation.daily.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.data.forecast.Daily
import com.mohamedabdallah.weather.utils.getDate
import com.mohamedabdallah.weather.utils.getSunSet

class DailyForecastAdapter(
        private var dailyForecastList: List<Daily>,
        private var listener : DailyForecastAdapter.OnDailyForecastListener?

) :
        RecyclerView.Adapter<DailyForecastAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO bind item

        val current:Daily=dailyForecastList[position]
        holder.date.text= getDate(current.dt)
        holder.temp.text=current.temp.day.toString()
        holder.time.text=getSunSet(current.dt)
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
        val view = layoutInflater.inflate(R.layout.daily_layout1
                , parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) :
            RecyclerView.ViewHolder(view),View.OnClickListener{
        var date: TextView = view.findViewById(R.id.daily_date)
        var time: TextView = view.findViewById(R.id.daily_time)
        var image: ImageView = view.findViewById(R.id.daily_image)
        var temp: TextView = view.findViewById(R.id.daily_temp)
        var cardView: CardView =view.findViewById(R.id.card_day)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener?.onDailyForecastItemClick(adapterPosition)
        }

    }
    interface OnDailyForecastListener
    {
        fun onDailyForecastItemClick(id:Int)
    }

    override fun getItemCount(): Int {
        return dailyForecastList.size
    }

    fun setData(list: List<Daily>) {
        dailyForecastList = list
        notifyDataSetChanged()

    }

    fun getData() : List<Daily>{
        return dailyForecastList
    }

}
