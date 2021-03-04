package com.mohamedabdallah.weather.data.forecast

import java.io.Serializable

data
class ForecastCustomizedModel(
        var temperature: String? = null,
        var weatherType: String? = null,
        var date: String? = null,
        var time: String? = null,
        var dayOfTheWeek: String? = null,
        var monthOfTheYear: String? = null,
        var dateOfTheMonth: Int = 0,
        var location: String? = null
):Serializable {
}