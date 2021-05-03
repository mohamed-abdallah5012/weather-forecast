package com.mohamedabdallah.weather.utils
object Constant {
     const val baseUrl = "https://api.openweathermap.org/data/2.5/"
     const val appID = "a3f4fff6fb2630d7167440f05dd3bb4a"
     //const val appID = "dbd3b02d8958d62185d02e944cd5f522"
     const val apiPlaces="AIzaSyDhtVSlNM52yj-vH7H7SMEFswg7CtaVCUQ"

     var api_lang="en"
      var baseUnit = "metric"
     const val exclude = "current,minutely"
     // standard, metric and imperial
     var appDefaultLanguage: String = "en"

     const val radarUrl="http://maps.goweatherradar.com/en/widget/"
     const val radarUrlExtension="1bfe4f546eb8a1d9fbe2f73812e60361e616c57d"
     const val radarAppID="XEBIYYivwDIjUX5YHiUSPM19SEOx7fsF"

      var myLat:Double=0.0
      var myLon:Double=0.0

     var currentLat:Double = 0.0
     var currentLon:Double = 0.0

     var notification= true


}