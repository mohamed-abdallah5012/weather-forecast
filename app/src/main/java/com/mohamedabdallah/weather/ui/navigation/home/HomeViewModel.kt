package com.mohamedabdallah.weather.ui.navigation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohamedabdallah.weather.data.forecast.ForecastCustomizedModel
import com.mohamedabdallah.weather.data.forecast.ForecastData
import com.mohamedabdallah.weather.data.forecast.ListWeatherInfo
import com.mohamedabdallah.weather.data.model.FavoritePlace
import com.mohamedabdallah.weather.data.weather.WeatherData
import com.mohamedabdallah.weather.repo.HomeRepository
import kotlinx.coroutines.launch
import java.util.ArrayList

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val homeRepository = HomeRepository(application)

    fun getWeatherByCityName(cityName: String, appId: String, lang: String, unit: String):
            LiveData<WeatherData> {
        return homeRepository.getWeatherByCityName(cityName, appId, lang, unit)
    }

    fun getWeatherByLatLong(latitude: String, longitude: String, appId: String, lang: String, unit: String):
            LiveData<WeatherData> {
        return homeRepository.getWeatherByLatLong(latitude, longitude, appId, lang, unit)
    }

    fun getWeatherForecastByCityName(cityName: String, appId: String, lang: String, unit: String,key:Boolean):
            LiveData<List<ForecastCustomizedModel>>? {
        var response = homeRepository.getWeatherForecastByCityName(cityName, appId, lang, unit)
        return if (key)
            response.value?.mList?.let { prepareForecastAdapterHour(it) }
        else
            response.value?.mList?.let { prepareForecastAdapterDaily(it) }

    }

    fun getWeatherForecastByLatLong(latitude: String, longitude: String, appId: String, lang: String, unit: String,key:Boolean):
            LiveData<List<ForecastCustomizedModel>>? {

        var response = homeRepository.getWeatherForecastByLatLong(latitude,longitude, appId, lang, unit)
        return if (key)
            response.value?.mList?.let { prepareForecastAdapterHour(it) }
        else
            response.value?.mList?.let { prepareForecastAdapterDaily(it) }
    }

    fun getFavoritePlaces(): LiveData<List<FavoritePlace>> {
        return homeRepository.getFavoritesPlaces()
    }
    fun addFavoritePlace(favoritePlace: FavoritePlace) {

        homeRepository.addFavoritePlace(favoritePlace)
    }


    private fun prepareForecastAdapterDaily(
        list: List<ListWeatherInfo>
    ): LiveData<List<ForecastCustomizedModel>> {
        val listOfForecastAdapterLiveData = MutableLiveData<List<ForecastCustomizedModel>>()
        val listOfForecastAdapter = ArrayList<ForecastCustomizedModel>()
        val uniqueDate = ArrayList<Int>()
        for (listWeatherInfo in list) {
            val dateOfTheMonth =
                listWeatherInfo.mDtTxt?.let { com.mohamedabdallah.weather.utils.getDateOfTheMonth(it) }
            if (uniqueDate.contains(dateOfTheMonth)) {
                continue
            }
            dateOfTheMonth?.let { uniqueDate.add(it) }
            val forecastCustomizedModel = ForecastCustomizedModel().apply {

                this.dayOfTheWeek =
                    com.mohamedabdallah.weather.utils.getDayOfTheWeek(listWeatherInfo.mDtTxt!!)
                        .toLowerCase() + " "

                this.dateOfTheMonth = dateOfTheMonth!!

                this.monthOfTheYear =
                    " " + com.mohamedabdallah.weather.utils.getMonthOfTheYear(listWeatherInfo.mDtTxt)
                        .toLowerCase()
                this.temperature = listWeatherInfo.mMain?.mTemp.toString()
                this.weatherType = listWeatherInfo.mWeather?.get(0)?.mMain
                this.time = com.mohamedabdallah.weather.utils.getTime(listWeatherInfo.mDtTxt)
            }
            listOfForecastAdapter.add(forecastCustomizedModel)
        }
        listOfForecastAdapterLiveData.value = listOfForecastAdapter

        return listOfForecastAdapterLiveData
    }

    private fun prepareForecastAdapterHour(
        list: List<ListWeatherInfo>
    ): LiveData<List<ForecastCustomizedModel>> {
        val listOfForecastAdapterLiveData = MutableLiveData<List<ForecastCustomizedModel>>()
        val listOfForecastAdapter = ArrayList<ForecastCustomizedModel>()
        val uniqueDate = ArrayList<Int>()
        for (listWeatherInfo in list) {
            val dateOfTheMonth =listWeatherInfo.mDtTxt?.let { com.mohamedabdallah.weather.utils.getDateOfTheMonth(it) }
           // if (!uniqueDate.contains(dateOfTheMonth)) { continue }
            dateOfTheMonth?.let { uniqueDate.add(it) }
            val forecastCustomizedModel = ForecastCustomizedModel().apply {

                this.dayOfTheWeek =
                    com.mohamedabdallah.weather.utils.getDayOfTheWeek(listWeatherInfo.mDtTxt!!)
                        .toLowerCase() + " "

                this.dateOfTheMonth = dateOfTheMonth!!

                this.monthOfTheYear =
                    " " + com.mohamedabdallah.weather.utils.getMonthOfTheYear(listWeatherInfo.mDtTxt)
                        .toLowerCase()
                this.temperature = listWeatherInfo.mMain?.mTemp.toString()
                this.weatherType = listWeatherInfo.mWeather?.get(0)?.mMain
                this.time = com.mohamedabdallah.weather.utils.getTime(listWeatherInfo.mDtTxt)
            }
            listOfForecastAdapter.add(forecastCustomizedModel)
        }
        listOfForecastAdapterLiveData.value = listOfForecastAdapter

        return listOfForecastAdapterLiveData
    }

}