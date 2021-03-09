package com.mohamedabdallah.weather.data.forecast

data class Daily(
    val clouds: Int,
    val dew_point: Double,
    val dt: Long,
    val feels_like: FeelsLike,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val rain: Double,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temp,
    val uvi: Double,
    val weather: List<WeatherX>,
    val wind_deg: Double,
    val wind_speed: Double
)