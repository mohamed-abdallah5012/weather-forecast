package com.mohamedabdallah.weather.ui.navigation.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView
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
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.data.favorite.FavoritePlace
import com.mohamedabdallah.weather.data.weather.CurrentResponse
import com.mohamedabdallah.weather.ui.navigation.daily.adapter.DailyForecastAdapter
import com.mohamedabdallah.weather.ui.navigation.favorite.FavoriteFragment
import com.mohamedabdallah.weather.ui.navigation.favorite.FavoriteViewModel
import com.mohamedabdallah.weather.ui.navigation.favorite.adapter.FavoriteCitiesAdapter
import com.mohamedabdallah.weather.ui.navigation.favorite.adapter.FavoriteListAdapter
import com.mohamedabdallah.weather.ui.navigation.home.adapter.HourlyForecastAdapter
import com.mohamedabdallah.weather.utils.*
import java.io.IOException

class HomeFragment : Fragment(),
    FavoriteCitiesAdapter.OnFavoritePlaceListener,
    FavoriteListAdapter.OnEditFavoriteListener {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var frameLayout: FrameLayout


    private lateinit var favoriteViewModel: FavoriteViewModel
    private var favoriteListAdapter = FavoriteListAdapter(emptyList(), this)
    private lateinit var favoriteListRecyclerView: RecyclerView


    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoriteRecyclerView: RecyclerView
    private lateinit var hourlyRecyclerView: RecyclerView
    private lateinit var dailyRecyclerView: RecyclerView
    private lateinit var ooo: TextView
    private lateinit var oooo: TextView
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
    private lateinit var tvHour: TextClock
    private lateinit var locationName: TextView
    private lateinit var locationIcon: ImageView
    private lateinit var degreesymbol: TextView
    private lateinit var humidity: TextView
    private lateinit var pressure: TextView
    private lateinit var visibility: TextView
    private lateinit var cloudiness: TextView
    private lateinit var sunset: TextView
    private lateinit var sunrise: TextView
    private lateinit var placesClient: PlacesClient
    private lateinit var manageFavorite: TextView

    private val favoriteCitiesAdapter =
        FavoriteCitiesAdapter(
            emptyList(),
            this
        )
    private val dailyForecastAdapter = DailyForecastAdapter(emptyList(), null)
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
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)



        this.context?.let { Places.initialize(it, Constant.apiPlaces) }
        this.context?.let { placesClient = Places.createClient(it) }


        bottomSheetBehavior.apply {
            peekHeight = 0
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }


        getMyLocation()
        autoCompleteSearch()
        locationIcon.setOnClickListener { getMyLocation() }
        manageFavorite.setOnClickListener { //navigateToFavoriteFragment()
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }


        favoriteRecyclerView.adapter = favoriteCitiesAdapter
        favoriteRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        favoriteRecyclerView.setHasFixedSize(true)

        hourlyRecyclerView.adapter = hourlyForecastAdapter
        hourlyRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        hourlyRecyclerView.setHasFixedSize(true)

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


        favoriteListRecyclerView.adapter = favoriteListAdapter
        favoriteListRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        favoriteListRecyclerView.setHasFixedSize(true)

        favoriteViewModel.getFavoritePlaces().observe(viewLifecycleOwner, Observer {

            favoriteListAdapter.setData(it)
        })
    }

    override fun onStart() {
        super.onStart()
        //getMyLocation()
    }

    private fun navigateToFavoriteFragment() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, FavoriteFragment())
            .commit()
    }

    private fun initialize(view: View) {

        frameLayout = view.findViewById(R.id.favorite_bottom_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(frameLayout)
        favoriteListRecyclerView = view.findViewById(R.id.edit_recycler_view)



        favoriteRecyclerView = view.findViewById(R.id.cities_recycler_view)
        hourlyRecyclerView = view.findViewById(R.id.hour_recycler_view)
        dailyRecyclerView = view.findViewById(R.id.day_recycler_view)
        manageFavorite = view.findViewById(R.id.manage_favorite)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view.context)

        temp = view.findViewById(R.id.tvTemperature)
        temperature = view.findViewById(R.id.temperature)

        ooo=view.findViewById(R.id.ooo)
        oooo=view.findViewById(R.id.oooo)
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
        degreesymbol = view.findViewById(R.id.degree_symbol)
        humidity = view.findViewById(R.id.txt_humidity)
        pressure = view.findViewById(R.id.txt_pressure)
        visibility = view.findViewById(R.id.txt_visibility)
        cloudiness = view.findViewById(R.id.txt_Cloudiness)
        sunrise = view.findViewById(R.id.txt_sunrise)
        sunset = view.findViewById(R.id.txt_sunset)


        tvDate.text = getCurrentDate()

    }

    private fun isLocationEnabled(mContext: Context): Boolean {
        val lm = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun showLocationIsDisabledAlert() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        builder.setMessage("Turn on Location Services ?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                val gpsOptionsIntent = Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS
                )
                startActivity(gpsOptionsIntent)
            }
            .setNegativeButton("No") { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.setTitle("Location Services is Disabled")
        alert.show()
    }

    private fun getMyLocation() {
        checkForPermission()
        if (!isLocationEnabled(requireContext())) {
            showLocationIsDisabledAlert()
        } else {

            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        Constant.myLat=location.latitude
                        Constant.myLon=location.longitude

                        Constant.currentLat=location.latitude
                        Constant.currentLon=location.longitude

                        getWeather(location.latitude, location.longitude)
                        getWeatherForecast(location.latitude, location.longitude)
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
                        Constant.myLat=lat
                        Constant.myLon=lng

                        Constant.currentLat=lat
                        Constant.currentLon=lng

                        getWeather(lat, lng)
                        getWeatherForecast(lat, lng)
                        getAddress(LatLng(lat, lng))

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
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                101
            )
            return
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
                            Constant.currentLat=currentPlace!!.latLng!!.latitude
                            Constant.currentLon=currentPlace!!.latLng!!.longitude

                            getWeather(currentPlace!!.latLng!!.latitude, currentPlace!!.latLng!!.longitude)
                            getWeatherForecast(currentPlace!!.latLng!!.latitude, currentPlace!!.latLng!!.longitude)

                            val path = saveToInternalStorage(bit, context!!, "${place.name}")
                            if (favoriteCitiesAdapter.getData().size < 13) {
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
                                            path!!
                                        )
                                    }
                                    .setNegativeButton("Cancel") { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    }
                                val alertDialog = alertDialogBuilder.create()
                                alertDialog.show()
                            }

                        }
                }

               /* placesClient.fetchPlace(
                    FetchPlaceRequest.builder(
                        place.id!!,
                        Arrays.asList(Place.Field.LAT_LNG)
                    ).build()
                )
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            currentPlace = task.result.place
                            getWeather(place.latLng!!.latitude, place.latLng!!.longitude)
                            getWeatherForecast(place.latLng!!.latitude, place.latLng!!.longitude)

                        } else {

                        }
                    })*/

                currentPlace.name?.let { setLocName(it) }
            }

            override fun onError(status: Status) {
                Log.i("TAG", "An error occurred: $status")

            }
        })

    }

    private fun setData(it: CurrentResponse) {

        cityName.isSelected = true
        cityName.text = it.name
        temp.text = it.main?.temp.toString()
        temperature.text = it.main?.temp.toString()

        minTemp.text = it.main?.temp_min.toString()
        maxTemp.text = it.main?.temp_max.toString()
        windSpeed.text = it.wind?.speed.toString()
        mainState.text = it.weather[0].main
        humidity.text = it.main?.humidity.toString() + " %"
        pressure.text = it.main?.pressure.toString() +" mbar"

        sunset.text = getSunSet(it.sys!!.sunset)
        sunrise.text = getSunSet(it.sys!!.sunrise)


        visibility.text = it.visibility?.toString() + " m"
        cloudiness.text = it.clouds?.all.toString() + " %"

        temperatureType.text = resources.getString(R.string.c)
        degreesymbol.text = resources.getString(R.string.c)
        ooo.text=resources.getString(R.string.o)
        oooo.text=resources.getString(R.string.o)
        //view.findViewById<TextView>(R.id.ooo).text=resources.getString(R.string.c)
        mainDesc.text = it.weather[0].main
        fullDesc.text = it.weather[0].description

        when (it.weather?.get(0)?.main) {

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
        Constant.currentLat=favoritePlace.lat
        Constant.currentLon=favoritePlace.lng
        setLocName(favoritePlace.name)
        Log.i("TAG", " favorite______________:${favoritePlace.lat} ")
        Log.i("TAG", "favorite______________:${favoritePlace.lng} ")
        getWeatherForecast(favoritePlace.lat, favoritePlace.lng)
        getWeather(favoritePlace.lat, favoritePlace.lng)

    }

    private fun getWeather(latitude: Double, longitude: Double) {
        viewModel.getWeatherByLatLong(
            latitude,
            longitude,
            Constant.appID,
            Constant.api_lang,
            Constant.baseUnit
        )
            ?.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    setData(it)
                   // Log.i("Home lat", "laa:${it.coord.lat} ")
                    //Log.i("Home Lon", "laa:${it.coord.lon} ")
                }
                else {
                    Log.i("TAG", "getWeather: dffffffffffff")
                }
            })
    }

    private fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ) {

        viewModel.getForecastOneApi(
            latitude,longitude,
            Constant.appID,
            Constant.api_lang,
            Constant.baseUnit
        )
            ?.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    hourlyForecastAdapter.setData(it.hourly)
                    dailyForecastAdapter.setData(it.daily)
                    Log.i("TAG", "laa:${it.lat} ")
                    Log.i("TAG", "laa:${it.lon} ")

                } else {
                    hourlyForecastAdapter.setData(emptyList())
                    dailyForecastAdapter.setData(emptyList())
                }
            })


    }

    fun addFavoritePlace(
        placeID: String,
        name: String,
        address: String,
        lat: Double,
        lng: Double,
        path: String
    ) {

        viewModel.addFavoritePlace(FavoritePlace(placeID!!, name, address, lat, lng, path))
    }

    private fun setLocName(value: String) {
        locationName.isSelected = true
        locationName.text = value
    }

    override fun onRemovePlaceClick(favoritePlace: FavoritePlace) {
        favoriteViewModel.deleteFavoritePlace(favoritePlace)
        favoriteViewModel.deleteCurrentResponse(favoritePlace.lat,favoritePlace.lng)
        favoriteViewModel.deleteForecastResponse(favoritePlace.lat,favoritePlace.lng)

    }
}