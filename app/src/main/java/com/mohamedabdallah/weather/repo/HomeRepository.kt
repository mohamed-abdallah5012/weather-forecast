package com.mohamedabdallah.weather.repo


import com.mohamedabdallah.weather.local.LocalSource
import com.mohamedabdallah.weather.remote.RemoteSource
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohamedabdallah.weather.data.favorite.FavoritePlace
import com.mohamedabdallah.weather.data.weather.CurrentResponse
import com.mohamedabdallah.weather.data.forecast.ForecastResponse
import com.mohamedabdallah.weather.utils.Constant
import kotlinx.coroutines.*

class HomeRepository(application: Application) {


    private val remote = RemoteSource()
    private val local = LocalSource(application)

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private var currentResponse: MutableLiveData<CurrentResponse>? =
        MutableLiveData<CurrentResponse>()
    private var forecastResponse: MutableLiveData<ForecastResponse>? =
        MutableLiveData<ForecastResponse>()


    fun getWeatherByLatLong(
        latitude: Double,
        longitude: Double,
        appId: String,
        lang: String,
        unit: String
    ):
            MutableLiveData<CurrentResponse>? {
        uiScope.launch(Dispatchers.Main) {
            currentResponse?.value = getCurrentFromLocal(latitude, longitude)

            try {
                val response = remote.getWeatherByLatLong(latitude, longitude, appId, lang, unit)
                if (response.isSuccessful) {
                    currentResponse?.value = response.body()
                    if (latitude == Constant.myLat && longitude == Constant.myLon) {
                        response.body()!!.currentResponseId = "myLocation"
                    } else {
                        response.body()!!.currentResponseId =
                            latitude.toString() + longitude.toString()
                    }
                    response.body()!!.coord.lat = latitude
                    response.body()!!.coord.lon = longitude

                    response.body()?.let { local.saveCurrentResponse(response.body()!!) }
                } else {
                    Log.i("TAG", "Get Message: ${response.message()}")
                }
            } catch (exception: Exception) {
                Log.i("TAG", ": $exception")
            }
        }
        //return local.getCurrentResponse(latitude, longitude)
        return currentResponse
    }

    fun getForecastOneApi(
        latitude: Double,
        longitude: Double,
        appId: String,
        lang: String,
        unit: String
    ):
            MutableLiveData<ForecastResponse>? {

        uiScope.launch(Dispatchers.Main) {
            forecastResponse?.value = getForecastFromLocal(latitude, longitude)
            try {
                val response = remote.getForecastOneApi(latitude, longitude, appId, lang, unit)
                if (response.isSuccessful) {
                    forecastResponse?.value = response.body()
                    if (latitude == Constant.myLat && longitude == Constant.myLon) {
                        response.body()!!.forecastResponseId = "myLocation"
                    } else {
                        response.body()!!.forecastResponseId =
                            latitude.toString() + longitude.toString()
                    }
                    response.body()!!.lat = latitude
                    response.body()!!.lon = longitude
                    response.body()?.let { local.saveForecastResponse(it) }
                } else {
                    Log.i("TAG", "Get Message: ${response.message()}")

                }
            } catch (exception: Exception) {
                Log.i("TAG", ": $exception")
            }
        }

        return forecastResponse
    }

    suspend fun deleteCurrentResponse(lat: Double, lon: Double) {
        local.deleteCurrentResponse(lat, lon)
    }

    suspend fun deleteForecastResponse(lat: Double, lon: Double) {
        local.deleteForecastResponse(lat, lon)
    }

    fun addFavoritePlace(favoritePlace: FavoritePlace) {
        uiScope.launch {
            local.addFavoritePlace(favoritePlace)
        }
    }

    suspend fun deleteFavoritePlace(favoritePlace: FavoritePlace) {
        local.deleteFavoritePlace(favoritePlace)
    }

    fun getFavoritesPlaces(): LiveData<List<FavoritePlace>> {
        return local.getFavoritesPlaces()
    }

    private suspend fun getCurrentFromLocal(lat: Double, lng: Double): CurrentResponse? {
        return local.getCurrentResponse(lat, lng)
    }

    private suspend fun getForecastFromLocal(lat: Double, lng: Double): ForecastResponse? {
        return local.getForecastResponse(lat, lng)
    }

    fun getWeatherAlert(lat: Double, lng: Double, type: String): Boolean {
        var key = false
        runBlocking {
            uiScope.launch(Dispatchers.IO) {
                var ccc = getCurrentFromLocal(lat, lng)
                try {
                    val response = remote.getWeatherByLatLong(
                        lat,
                        lng,
                        Constant.appID,
                        Constant.api_lang,
                        Constant.baseUnit
                    )
                    if (response.isSuccessful) {
                        ccc = response.body()
                        if (lat == Constant.myLat && lng == Constant.myLon) {
                            ccc!!.currentResponseId = "myLocation"
                        } else {
                            ccc!!.currentResponseId =
                                lat.toString() + lng.toString()
                        }
                        ccc!!.coord.lat = lat
                        ccc!!.coord.lon = lng

                        ccc.let { local.saveCurrentResponse(ccc!!) }

                    } else {
                        Log.i("TAG", "Get Message: ${response.message()}")
                    }
                } catch (exception: Exception) {
                    Log.i("TAG", ": $exception")
                }
                if (ccc?.weather?.get(0)?.main?.toUpperCase() == type.toUpperCase())
                    key = true
                Log.i("TAG", "hffffffff: ${ccc?.weather?.get(0)?.main?.toUpperCase()}")
                Log.i("TAG", "hffffffffjjj: ${type.toUpperCase()}")

            }.join()

        }
        return key
    }

}