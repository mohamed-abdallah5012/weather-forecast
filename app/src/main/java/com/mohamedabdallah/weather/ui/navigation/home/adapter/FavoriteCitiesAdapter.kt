package com.mohamedabdallah.weather.ui.navigation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.data.model.FavoritePlace
import com.mohamedabdallah.weather.ui.navigation.favorite.adapter.FavoriteListAdapter

class FavoriteCitiesAdapter(
        private var favoritesPlaces: List<FavoritePlace>,
        private var listener : OnFavoritePlaceListener
) :
        RecyclerView.Adapter<FavoriteCitiesAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO bind item
        holder.cityName.text=favoritesPlaces[position].name
        holder.iconUrl.setImageBitmap(favoritesPlaces[position].bitmap)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // TODO createView

        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.city_layout1
                , parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) :
            RecyclerView.ViewHolder(view),View.OnClickListener {
        var iconUrl: ImageView = view.findViewById(R.id.imageView)
        var cityName: TextView = view.findViewById(R.id.cities_title)


        init {
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onFavoritePlaceClick(favoritesPlaces[adapterPosition])
        }

    }
     interface OnFavoritePlaceListener
    {
        fun onFavoritePlaceClick(favoritePlace: FavoritePlace)
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
