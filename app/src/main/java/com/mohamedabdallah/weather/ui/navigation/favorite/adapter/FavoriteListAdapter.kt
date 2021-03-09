package com.mohamedabdallah.weather.ui.navigation.favorite.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.data.favorite.FavoritePlace

class FavoriteListAdapter(
    private var favoritesPlaces: List<FavoritePlace>,
    private var listener : OnEditFavoriteListener
) :
    RecyclerView.Adapter<FavoriteListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO bind item
        holder.cityName.text=favoritesPlaces[position].name
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // TODO createView

        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.edit_city_layout1
            , parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view),View.OnClickListener {
        var removeIcon: ImageView = view.findViewById(R.id.remove_city_edit_favorite)
        var cityName: TextView = view.findViewById(R.id.city_name_edit_favorite)

        init {
            removeIcon.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            listener.onRemovePlaceClick(favoritesPlaces[adapterPosition])
        }
    }
    interface OnEditFavoriteListener
    {
        fun onRemovePlaceClick(favoritePlace: FavoritePlace)
    }

    override fun getItemCount(): Int {
        return favoritesPlaces.size
    }

    fun setData(list: List<FavoritePlace>) {
        favoritesPlaces = list
        notifyDataSetChanged()

    }

    fun getData() : List<FavoritePlace>{
        return favoritesPlaces
    }
}
