package com.mohamedabdallah.weather.data.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoritePlace(
    @PrimaryKey
    val id :String,
    val name :String,
    val address :String,
    val lat: Double,
    val lng: Double,
    val path :String
) {
}