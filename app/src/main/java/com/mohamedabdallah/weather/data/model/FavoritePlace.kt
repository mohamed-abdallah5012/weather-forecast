package com.mohamedabdallah.weather.data.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity
data class FavoritePlace(
    @PrimaryKey
    val id :String,
    val name :String,
    val address :String,
    val lat: Double,
    val lng: Double,
    val bitmap :Bitmap
) {
}