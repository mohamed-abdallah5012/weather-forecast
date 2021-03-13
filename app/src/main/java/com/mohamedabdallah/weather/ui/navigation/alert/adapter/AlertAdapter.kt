package com.mohamedabdallah.weather.ui.navigation.alert.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dpro.widgets.WeekdaysPicker
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.data.alert.AlertResponse
import com.mohamedabdallah.weather.data.favorite.FavoritePlace
import com.mohamedabdallah.weather.ui.navigation.favorite.adapter.FavoriteListAdapter

class AlertAdapter(
        private var alertResponseList: List<AlertResponse>,
        private var listener : AlertAdapter.OnAlarmlistener


) :
        RecyclerView.Adapter<AlertAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO bind item

        val current:AlertResponse=alertResponseList[position]
        holder.title.text= current.alertTitle
        holder.type.text= current.alertType
        holder.days.selectedDays=current.selectedDays
        holder.days.setEditable(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // TODO createView

        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.alarm_row
                , parent, false)
        return ViewHolder(view)
    }
    inner class ViewHolder(view: View) :
            RecyclerView.ViewHolder(view),View.OnClickListener {
        var title: TextView = view.findViewById(R.id.alarm_tv_title)
        var type: TextView = view.findViewById(R.id.alarm_tv_type)
        //var time: Button = view.findViewById(R.id.alarm_tv_time)
        var edit: ImageButton = view.findViewById(R.id.alarm_iv_edit)
        var delete: ImageButton = view.findViewById(R.id.alarm_iv_delete)
        var days: WeekdaysPicker = view.findViewById(R.id.weekdays)
        //var citiies: WeekdaysPicker = view.findViewById(R.id.cities)

        init {
            delete.setOnClickListener(this)
            edit.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            if (p0==delete)
            listener.onRemoveAlarmClick(alertResponseList[adapterPosition].id)
            else
                if (p0==edit)
            listener.onEditAlarmClick(alertResponseList[adapterPosition])
        }
    }
    interface OnAlarmlistener
    {
        fun onRemoveAlarmClick(id: Long)
        fun onEditAlarmClick(alert: AlertResponse)
    }

    override fun getItemCount(): Int {
        return alertResponseList.size
    }

    fun setData(list: List<AlertResponse>) {
        alertResponseList = list
        notifyDataSetChanged()

    }

    fun getData() : List<AlertResponse>{
        return alertResponseList
    }

}
