package com.mohamedabdallah.weather.ui.navigation.favorite

import android.app.Application
import androidx.lifecycle.*
import com.mohamedabdallah.weather.data.model.FavoritePlace
import com.mohamedabdallah.weather.repo.HomeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
}