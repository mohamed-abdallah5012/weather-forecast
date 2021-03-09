package com.mohamedabdallah.weather.ui.navigation.favorite.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.data.favorite.FavoritePlace
import com.mohamedabdallah.weather.utils.loadImageFromStorage

class FavoriteCitiesAdapter(
        private var favoritesPlaces: List<FavoritePlace>,
        private var listener : OnFavoritePlaceListener
) :
        RecyclerView.Adapter<FavoriteCitiesAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO bind item
        val current=favoritesPlaces[position]
        holder.cityName.text=favoritesPlaces[position].name
        Log.i("TAG", "onBindViewHolder: ${current.path}")
        Log.i("TAG", "onBindViewHolder: ${current.name}.jpg")
        holder.iconUrl.setImageBitmap(loadImageFromStorage(current.path,"${current.name}.jpg"))
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
