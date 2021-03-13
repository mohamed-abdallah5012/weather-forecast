package com.mohamedabdallah.weather.data.alert

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class AlertResponse(
    var oneTimeRequests: List<UUID>,
    var alertTitle: String,
    var alertType: String,
    var selectedDays: List<Int>
)
{
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
}