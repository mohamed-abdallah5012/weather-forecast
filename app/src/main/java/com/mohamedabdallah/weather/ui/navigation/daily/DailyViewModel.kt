package com.mohamedabdallah.weather.ui.navigation.daily

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohamedabdallah.weather.repo.HomeRepository
import com.mohamedabdallah.weather.data.forecast.ForecastResponse

class DailyViewModel(application: Application) : AndroidViewModel(application) {

    private val homeRepository = HomeRepository(application)

    fun getForecastOneApi(latitude: Double, longitude: Double, appId: String, lang: String, unit: String):
            MutableLiveData<ForecastResponse>? {
        return homeRepository.getForecastOneApi(latitude, longitude, appId, lang, unit)
    }
}