package com.mohamedabdallah.weather.ui.navigation.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {

    var currentMode =MutableLiveData<String>("temp")
    var isLoading =MutableLiveData<Boolean>(true)

    fun getAvailableModes():Array<String>
    {
        return choices
    }
    fun setCurrentMode(mode:String)
    {
        currentMode.value=mode
        isLoading.value=false
    }

    companion object{
        val choices = arrayOf("temp", "rain", "wind","pressure","clouds","humidity")
    }
}