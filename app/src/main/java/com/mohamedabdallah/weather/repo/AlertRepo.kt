package com.mohamedabdallah.weather.repo

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.mohamedabdallah.weather.data.alert.AlertResponse
import com.mohamedabdallah.weather.data.forecast.Alert
import com.mohamedabdallah.weather.data.forecast.ForecastResponse
import com.mohamedabdallah.weather.data.weather.CurrentResponse
import com.mohamedabdallah.weather.local.LocalSource
import com.mohamedabdallah.weather.remote.RemoteSource
import com.mohamedabdallah.weather.utils.Constant
import kotlinx.coroutines.*

class AlertRepo (application: Application) {

    private val remote = RemoteSource()
    private val local = LocalSource(application)

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    suspend fun deleteAlertItem(id:Long)
    {
        local.deleteAlertItem(id)
    }
    fun addAlertList(alert: AlertResponse):Long
    {
        return local.addAlertList(alert)
    }
    fun getAlertList(): LiveData<List<AlertResponse>> {
        return local.getAlertList()
    }
    private suspend fun getForecastFromLocal(lat:Double,lng:Double): ForecastResponse? {
        return local.getForecastResponse(lat, lng)
    }
    fun getGovernmentAlert(lat:Double,lng:Double):MutableList<Alert>
    {
        var list= mutableListOf<Alert>()
        runBlocking {

            uiScope.launch(Dispatchers.Main) {
                var ccc=getForecastFromLocal(lat,lng)
                try {
                    val response = remote.getForecastOneApi(lat, lng, Constant.appID, Constant.api_lang, Constant.baseUnit)
                    if (response.isSuccessful) {
                        ccc=response.body()
                        if (lat == Constant.myLat && lng == Constant.myLon) {
                            ccc!!.forecastResponseId = "myLocation"
                        } else {
                            ccc!!.forecastResponseId =
                                lat.toString() + lng.toString()
                        }
                        ccc!!.lat=lat
                        ccc!!.lon=lng
                        ccc?.let { local.saveForecastResponse(ccc!!) }
                    } else {
                        Log.i("TAG", "Get Message: ${response.message()}")
                    }
                } catch (exception: Exception) {
                    Log.i("TAG", ": $exception")
                }

                if (ccc?.alerts!=null)
                    list= ccc.alerts as MutableList<Alert>
                else
                    list= emptyList<Alert>() as MutableList<Alert>
            }.join()
        }
        return list;
    }
}
