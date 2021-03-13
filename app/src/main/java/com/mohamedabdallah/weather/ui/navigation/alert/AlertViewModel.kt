package com.mohamedabdallah.weather.ui.navigation.alert

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mohamedabdallah.weather.data.alert.AlertResponse
import com.mohamedabdallah.weather.repo.AlertRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AlertViewModel(application: Application) : AndroidViewModel(application) {

    private val alertRepo = AlertRepo(application)


    fun getAlertList(): LiveData<List<AlertResponse>> {
        return alertRepo.getAlertList()
    }

    fun addAlertList(alert: AlertResponse): Long {


        return alertRepo.addAlertList(alert)

    }

    /* fun updateWorker(alertId:String,newAlert: AlertResponse)
     {
         viewModelScope.launch {
             alertRepo.deleteAlertItem(alertId)
             alertRepo.addAlertList(newAlert)
         }
     }

     */
    fun deleteAlertItem(id: Long) {
        viewModelScope.launch {
            alertRepo.deleteAlertItem(id)
        }
    }
}