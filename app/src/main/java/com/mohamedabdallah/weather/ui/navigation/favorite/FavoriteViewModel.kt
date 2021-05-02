package com.mohamedabdallah.weather.ui.navigation.favorite

import android.app.Application
import androidx.lifecycle.*
import com.mohamedabdallah.weather.data.favorite.FavoritePlace
import com.mohamedabdallah.weather.repo.HomeRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel


    private val homeRepository = HomeRepository(application)

     fun getFavoritePlaces(): LiveData<List<FavoritePlace>> {
        return homeRepository.getFavoritesPlaces()
    }
    fun deleteFavoritePlace(favoritePlace: FavoritePlace)
    {
        viewModelScope.launch {
            homeRepository.deleteFavoritePlace(favoritePlace)
        }
    }


    fun deleteCurrentResponse(lat:Double,lan:Double)
    {
        viewModelScope.launch {
            homeRepository.deleteCurrentResponse(lat,lan)
        }
    }
    fun deleteForecastResponse(lat:Double,lan:Double)
    {
        viewModelScope.launch {
            homeRepository.deleteForecastResponse(lat,lan)
        }
    }

}