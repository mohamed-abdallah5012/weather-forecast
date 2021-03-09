package com.mohamedabdallah.weather.ui.navigation.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohamedabdallah.weather.data.favorite.FavoritePlace
import com.mohamedabdallah.weather.data.weather.CurrentResponse
import com.mohamedabdallah.weather.repo.HomeRepository
import com.mohamedabdallah.weather.data.forecast.ForecastResponse
import com.mohamedabdallah.weather.utils.Constant

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val homeRepository = HomeRepository(application)

    fun getForecastOneApi(latitude: Double, longitude: Double, appId: String, lang: String, unit: String):
            MutableLiveData<ForecastResponse>? {

        Constant.currentLat=latitude
        Constant.currentLon=longitude
        return homeRepository.getForecastOneApi(latitude, longitude, appId, lang, unit)
    }


    fun getWeatherByLatLong(latitude: Double, longitude: Double, appId: String, lang: String, unit: String):
            MutableLiveData<CurrentResponse>? {
        Constant.currentLat=latitude
        Constant.currentLon=longitude
        return homeRepository.getWeatherByLatLong(latitude, longitude, appId, lang, unit)
    }

    fun getFavoritePlaces(): LiveData<List<FavoritePlace>> {
        return homeRepository.getFavoritesPlaces()
    }
    fun addFavoritePlace(favoritePlace: FavoritePlace) {

        homeRepository.addFavoritePlace(favoritePlace)
    }


}