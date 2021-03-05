package com.mohamedabdallah.weather.ui.navigation.home.adapter

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

class DailyForecastAdapter(
        private var dailyForecastList: List<ForecastCustomizedModel>,
        private var listener : DailyForecastAdapter.OnDailyForecastListener?

) :
        RecyclerView.Adapter<DailyForecastAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO bind item

        val current:ForecastCustomizedModel=dailyForecastList[position]
        holder.date.text=current.dayOfTheWeek+current.dateOfTheMonth+current.monthOfTheYear
        holder.temp.text=current.temperature
        holder.time.text=current.time
        // image
        when(current.weatherType){

            holder.itemView.resources.getString(R.string.clouds) -> {
                holder.cardView.setCardBackgroundColor(R.color.lightBlue)
                holder.image.setImageResource(R.drawable.ic_cloudy)

            }
            holder.itemView.resources.getString(R.string.rain) ->{
                holder.cardView.setCardBackgroundColor(R.color.lightSlateGray)
                holder.image.setImageResource(R.drawable.ic_rain)

            }
            holder.itemView.resources.getString(R.string.clear) ->{
                holder.cardView.setCardBackgroundColor(R.color.lightYellow)
                holder.image.setImageResource(R.drawable.ic_sunny)

            }
            holder.itemView.resources.getString(R.string.thunderstorm) ->{
                holder.cardView.setCardBackgroundColor(R.color.darkSlateBlue)
                holder.image.setImageResource(R.drawable.ic_thunderstorm)

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
            listener?.onDailyForecastItemClick(dailyForecastList[adapterPosition])
        }

    }
    interface OnDailyForecastListener
    {
        fun onDailyForecastItemClick(forcastList: ForecastCustomizedModel)
    }

    override fun getItemCount(): Int {
        return dailyForecastList.size
    }

    fun setData(list: List<ForecastCustomizedModel>) {
        dailyForecastList = list
        notifyDataSetChanged()

    }

    fun getData() : List<ForecastCustomizedModel>{
        return dailyForecastList
    }

}
