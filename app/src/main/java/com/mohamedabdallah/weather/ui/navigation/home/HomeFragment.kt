package com.mohamedabdallah.weather.ui.navigation.home

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.data.model.FavoritePlace
import com.mohamedabdallah.weather.data.weather.WeatherData
import com.mohamedabdallah.weather.ui.navigation.daily.DailyFragment
import com.mohamedabdallah.weather.ui.navigation.favorite.FavoriteFragment
import com.mohamedabdallah.weather.ui.navigation.home.adapter.DailyForecastAdapter
import com.mohamedabdallah.weather.ui.navigation.favorite.adapter.FavoriteListAdapter
import com.mohamedabdallah.weather.ui.navigation.home.adapter.FavoriteCitiesAdapter
import com.mohamedabdallah.weather.ui.navigation.home.adapter.HourlyForecastAdapter
import com.mohamedabdallah.weather.ui.navigation.map.MapFragment
import com.mohamedabdallah.weather.utils.Constant
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(), FavoriteCitiesAdapter.OnFavoritePlaceListener {

    companion object {
        fun newInstance() =
            HomeFragment()
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoriteRecyclerView: RecyclerView
    private lateinit var hourlyRecyclerView: RecyclerView
    private lateinit var dailyRecyclerView: RecyclerView
    private lateinit var temp: TextView
    private lateinit var temperature: TextView
    private lateinit var minTemp: TextView
    private lateinit var maxTemp: TextView
    private lateinit var windSpeed: TextView
    private lateinit var mainState: TextView
    private lateinit var cityName: TextView
    private lateinit var mainDesc: TextView
    private lateinit var fullDesc: TextView
    private lateinit var temperatureType: TextView
    private lateinit var mainStateview: ImageView
    private lateinit var weatherIcon: ImageView
    private lateinit var tvDate: TextView
    private lateinit var tvHour: TextView
    private lateinit var locationName: TextView
    private lateinit var locationIcon: ImageView
    private lateinit var humidity: TextView
    private lateinit var pressure: TextView
    private lateinit var visibility: TextView
    private lateinit var cloudiness: TextView
    private lateinit var sunset: TextView
    private lateinit var sunrise: TextView
    private lateinit var placesClient: PlacesClient
    private lateinit var manageFavorite: TextView

    private val favoriteCitiesAdapter = FavoriteCitiesAdapter(emptyList(), this)
    private val dailyForecastAdapter = DailyForecastAdapter(emptyList())
    private val hourlyForecastAdapter = HourlyForecastAdapter(emptyList())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        initialize(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        this.context?.let { Places.initialize(it, Constant.apiPlaces) }
        this.context?.let { placesClient = Places.createClient(it) }


        //getMyLocation()
        autoCompleteSearch()
        locationIcon.setOnClickListener { getMyLocation() }
        manageFavorite.setOnClickListener { navigateToFavoriteFragment() }


        favoriteRecyclerView.adapter = favoriteCitiesAdapter
        favoriteRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        favoriteRecyclerView.setHasFixedSize(true)

        hourlyRecyclerView.adapter = hourlyForecastAdapter
        hourlyRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        hourlyRecyclerView.setHasFixedSize(true)
        //hourlyRecyclerView.setItemViewCacheSize(20)

        dailyRecyclerView.adapter = dailyForecastAdapter
        dailyRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dailyRecyclerView.setHasFixedSize(true)


        viewModel.getFavoritePlaces().observe(viewLifecycleOwner, Observer {
            favoriteCitiesAdapter.setData(it)
           // if (locationName.text.isNullOrEmpty())
             //  if (it.isNotEmpty())
               //     getWeather(it[0].name)
              // else
                   // Toast.makeText(context,"You Must Enable",Toast.LENGTH_SHORT).show()
                //   autoCompleteSearch()
        })
    }
    private fun navigateToFavoriteFragment() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, FavoriteFragment())
            .commit()
    }
    private fun initialize(view: View) {
        favoriteRecyclerView = view.findViewById(R.id.cities_recycler_view)
        hourlyRecyclerView = view.findViewById(R.id.hour_recycler_view)
        dailyRecyclerView = view.findViewById(R.id.day_recycler_view)
        manageFavorite = view.findViewById(R.id.manage_favorite)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view.context)

        temp = view.findViewById(R.id.tvTemperature)
        temperature = view.findViewById(R.id.temperature)

        minTemp = view.findViewById(R.id.min_temp)
        maxTemp = view.findViewById(R.id.max_temp)
        windSpeed = view.findViewById(R.id.tvWind_speed)
        mainState = view.findViewById(R.id.main_state)
        cityName = view.findViewById(R.id.city_name)
        mainDesc = view.findViewById(R.id.main_desc)
        fullDesc = view.findViewById(R.id.full_desc)
        mainStateview = view.findViewById(R.id.ivPrecipType)
        weatherIcon = view.findViewById(R.id.weather_icon)
        temperatureType = view.findViewById(R.id.tvTypeTemperature)
        tvDate = view.findViewById(R.id.tvDate)
        tvHour = view.findViewById(R.id.tvHour)
        locationName = view.findViewById(R.id.location_name)
        locationIcon = view.findViewById(R.id.imageLocation)
        // searchIcon = view.findViewById(R.id.imageSearch)
        humidity = view.findViewById(R.id.txt_humidity)
        pressure = view.findViewById(R.id.txt_pressure)
        visibility = view.findViewById(R.id.txt_visibility)
        cloudiness = view.findViewById(R.id.txt_Cloudiness)
        sunrise = view.findViewById(R.id.txt_sunrise)
        sunset = view.findViewById(R.id.txt_sunset)

        val c: Calendar = Calendar.getInstance()
        val currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.time)
        val simpleTimeFormat = SimpleDateFormat("hh:mm")
        val currentTime = simpleTimeFormat.format(c.time)
        tvDate.text = currentDate
        tvHour.text = currentTime
    }

    private fun isLocationEnabled(mContext: Context): Boolean {
        val lm = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun showLocationIsDisabledAlert() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(context!!)

        builder.setMessage("Turn on Location Services ?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                val gpsOptionsIntent = Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS
                )
                startActivity(gpsOptionsIntent)
            }
            .setNegativeButton("No") { dialog, id ->  dialog.cancel()}
        val alert = builder.create()
        alert.setTitle("Location Services is Disabled")
        alert.show()
    }

    private fun getMyLocation() {
        checkForPermission()
        if (!isLocationEnabled(context!!)) {
            showLocationIsDisabledAlert()
        } else {

            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        Log.i("TAG", "getMyLocation: mohamed")
                        getWeather(location.latitude.toString(),location.longitude.toString())
                        getWeatherForecast(location.latitude.toString(),location.longitude.toString())
                        getAddress(LatLng(location.latitude, location.longitude))

                    } else {
                        getMyLoc()

                    }
                }
        }
    }
    private fun getMyLoc() {
        val locationRequest = LocationRequest.create()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        checkForPermission()
        LocationServices.getFusedLocationProviderClient(requireActivity())
            .requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    LocationServices.getFusedLocationProviderClient(requireActivity())
                        .removeLocationUpdates(this)
                    if (locationResult != null && locationResult.locations.size > 0) {
                        val index = locationResult.locations.size - 1
                        val lat =
                            locationResult.locations[index].latitude
                        val lng =
                            locationResult.locations[index].longitude
                          getWeather(lat.toString(),lng.toString())
                          getWeatherForecast(lat.toString(),lng.toString())
                          getAddress(LatLng(lat,lng))

                    }
                }
            }, Looper.getMainLooper())
    }

    private fun getAddress(latLng: LatLng?) {
        val geocoder = Geocoder(context)
        try {
            if (latLng != null) {
                val addressList =
                    geocoder.getFromLocation(latLng.latitude, latLng.longitude, 3)
                if (addressList != null && addressList.isNotEmpty()) {
                    val one = addressList[0]
                    val address = one.getAddressLine(0)
                    val city = one.locality
                    val state = one.adminArea
                    val country = one.countryName
                    val postalCode = one.postalCode
                    val knownName = one.featureName
                    setLocName("$address $city")
                }
            }
        } catch (e: IOException) {
        }
    }

    private fun checkForPermission() {

        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                101
            )
            return
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //getMyLocation()
            }
        }
    }

    private fun autoCompleteSearch() {

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                as AutocompleteSupportFragment
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.PHOTO_METADATAS,
                Place.Field.LAT_LNG
            )
        )

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {

                var currentPlace = place
                val photoMetaData = place.photoMetadatas?.get(0)

                photoMetaData?.let { FetchPhotoRequest.builder(it).build() }?.let {
                    placesClient.fetchPhoto(it)
                        .addOnSuccessListener { fetchPhotoResponse ->
                            val bit: Bitmap = fetchPhotoResponse.bitmap

                            // Log.i("TAG", "attributions: ${place.photoMetadatas!![0].attributions}")

                            if (favoriteCitiesAdapter.getData().size < 5) {
                                val alertDialogBuilder = android.app.AlertDialog.Builder(context)
                                    .setTitle("Add To Your Favorite")
                                    .setMessage("Add ${place.name} To Your Favorite List")
                                    .setPositiveButton("Ok") { dialogInterface, i ->
                                        addFavoritePlace(
                                            place.id!!,
                                            "${place.name}",
                                            "${place.address}",
                                            currentPlace!!.latLng!!.latitude,
                                            currentPlace!!.latLng!!.longitude,
                                            bit
                                        )

                                    }
                                    .setNegativeButton("Cancel") { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    }
                                val alertDialog = alertDialogBuilder.create()
                                alertDialog.show()
                            }

                            Log.i("TAG", "attributions: ${place.photoMetadatas!![0].attributions}")

                        }
                }

                placesClient.fetchPlace(
                    FetchPlaceRequest.builder(
                        place.id!!,
                        Arrays.asList(Place.Field.LAT_LNG)
                    ).build()
                )
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            currentPlace = task.result.place
                            place.name?.let { getWeather(it) }
                            place.name?.let { getWeatherForecast(it) }

                        } else {
                            Log.i("TAG", "onPlaceSelected: ${task.exception}")
                        }
                    })

                currentPlace.name?.let { setLocName(it) }
            }

            override fun onError(status: Status) {
                Log.i("TAG", "An error occurred: $status")

            }
        })

    }
    private fun setData(it: WeatherData) {

        cityName.isSelected=true
        cityName.text=it.mName
        temp.text = it.mMain?.mTemp.toString()
        temperature.text = it.mMain?.mTemp.toString()

        minTemp.text = it.mMain?.mTempMin.toString()
        maxTemp.text = it.mMain?.mTempMax.toString()
        windSpeed.text = it.mWind?.mSpeed.toString()
        mainState.text =it.mWeather[0].mMain
        humidity.text = it.mMain?.mHumidity.toString() + " %"
        pressure.text = it.mMain?.mPressure.toString()

        val sunsetDate = Date(it.mSys!!.mSunset * 1000L)
        val sunriseDate = Date(it.mSys!!.mSunrise * 1000L)

        val simpleFormat = SimpleDateFormat("hh:mm a")
        sunset.text = simpleFormat.format(sunsetDate.time)
        sunrise.text = simpleFormat.format(sunriseDate.time)


        visibility.text = it.mVisibility?.toString() + " meter"
        cloudiness.text = it.mClouds?.mAll.toString() + " %"

        // temperatureType.text
        mainDesc.text = it.mWeather[0].mMain
        fullDesc.text = it.mWeather[0].mDescription

        when (it.mWeather?.get(0)?.mMain) {

            resources.getString(R.string.clouds) -> {
                mainStateview.setImageResource(R.drawable.ic_cloudy)
                weatherIcon.setImageResource(R.drawable.ic_cloudy)

            }
            resources.getString(R.string.rain) -> {
                mainStateview.setImageResource(R.drawable.ic_rain)
                weatherIcon.setImageResource(R.drawable.ic_rain)

            }
            resources.getString(R.string.clear) -> {
                mainStateview.setImageResource(R.drawable.ic_sunny)
                weatherIcon.setImageResource(R.drawable.ic_sunny)

            }
            resources.getString(R.string.thunderstorm) -> {
                mainStateview.setImageResource(R.drawable.ic_thunderstorm)
                weatherIcon.setImageResource(R.drawable.ic_thunderstorm)

            }
            else -> {
                mainStateview.setImageResource(R.drawable.ic_thunderstorm)
                weatherIcon.setImageResource(R.drawable.ic_thunderstorm)
            }
        }

    }
    override fun onFavoritePlaceClick(favoritePlace: FavoritePlace) {
        setLocName(favoritePlace.name)
        getWeather(favoritePlace.name)
        getWeatherForecast(favoritePlace.name)
    }
    private fun getWeather(cityName: String) {
        viewModel.getWeatherByCityName(
            cityName,
            Constant.appID,
            Constant.english_lang,
            Constant.baseUnit
        )
            .observe(viewLifecycleOwner, Observer {
                setData(it)
            })
    }

    private fun getWeather(latitude: String, longitude: String) {
        viewModel.getWeatherByLatLong(
            latitude,
            longitude,
            Constant.appID,
            Constant.english_lang,
            Constant.baseUnit
        )
            .observe(viewLifecycleOwner, Observer {
                setData(it)
            })
    }

    private fun getWeatherForecast(cityName: String) {
        viewModel.getWeatherForecastByCityName(
            cityName,
            Constant.appID,
            Constant.english_lang,
            Constant.baseUnit,
            true
        )
            ?.observe(viewLifecycleOwner, Observer {
                hourlyForecastAdapter.setData(it)
            })
        viewModel.getWeatherForecastByCityName(
            cityName,
            Constant.appID,
            Constant.english_lang,
            Constant.baseUnit,
            false
        )
            ?.observe(viewLifecycleOwner, Observer {
                dailyForecastAdapter.setData(it)
            })
    }

    private fun getWeatherForecast(latitude: String, longitude: String) {

        viewModel.getWeatherForecastByLatLong(
            latitude,
            longitude,
            Constant.appID,
            Constant.english_lang,
            Constant.baseUnit,
            true
        )
            ?.observe(viewLifecycleOwner, Observer {
                hourlyForecastAdapter.setData(it)
            })
        viewModel.getWeatherForecastByLatLong(
            latitude,
            longitude,
            Constant.appID,
            Constant.english_lang,
            Constant.baseUnit,
            false
        )
            ?.observe(viewLifecycleOwner, Observer {
                dailyForecastAdapter.setData(it)
            })


    }

    fun addFavoritePlace(
        placeID: String,
        name: String,
        address: String,
        lat: Double,
        lng: Double,
        bit: Bitmap
    ) {

        viewModel.addFavoritePlace(FavoritePlace(placeID!!, name, address, lat, lng, bit))
    }

    private fun setLocName(value: String) {
        locationName.isSelected = true
        locationName.text = value
    }

}