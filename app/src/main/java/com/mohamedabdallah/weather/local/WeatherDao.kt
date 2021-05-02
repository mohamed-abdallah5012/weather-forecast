package com.mohamedabdallah.weather.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mohamedabdallah.weather.data.alert.AlertResponse
import com.mohamedabdallah.weather.data.favorite.FavoritePlace
import com.mohamedabdallah.weather.data.forecast.ForecastResponse
import com.mohamedabdallah.weather.data.weather.CurrentResponse

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoritePlace(item: FavoritePlace)

    @Delete
    suspend fun deleteFavoritePlace(item: FavoritePlace)

    @Query("select * from FavoritePlace ")
    fun getFavoritesPlaces(): LiveData<List<FavoritePlace>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveForecastResponse(item: ForecastResponse)

    @Query("select * from ForecastResponse where lat-:latt<=0.1 and lon-:lngg<=0.1 ")
    suspend fun getForecastResponse(latt:Double,lngg:Double):ForecastResponse?

    @Query("delete from ForecastResponse where lat-:latt<=0.1 and lon-:lngg<=0.1")
    suspend fun deleteForecastResponse(latt:Double,lngg:Double)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrentResponse(item: CurrentResponse)

    @Query("select * from CurrentResponse where lat is:latt and lon is:lngg ")
    suspend fun getCurrentResponse(latt:Double,lngg:Double): CurrentResponse?

    @Query("delete from CurrentResponse where lat is:latt and lon is:lngg")
    suspend fun deleteCurrentResponse(latt:Double,lngg:Double)



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAlertList(alert: AlertResponse):Long

    //@Query("update AlertResponse where id")
    //suspend fun updateAlert(alert: AlertResponse)

    @Query("delete from AlertResponse where id is :id")
    suspend fun deleteAlertItem(id: Long)

    @Query("select * from AlertResponse ")
    fun getAlertList(): LiveData<List<AlertResponse>>


}