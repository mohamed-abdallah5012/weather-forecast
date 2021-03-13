package com.mohamedabdallah.weather.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.AnimationDrawable
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.ui.navigation.daily.DailyFragment
import com.mohamedabdallah.weather.ui.navigation.home.HomeFragment
import com.mohamedabdallah.weather.ui.navigation.map.MapFragment
import com.mohamedabdallah.weather.ui.navigation.settings.SettingsFragment
import com.mohamedabdallah.weather.utils.Constant
import com.mohamedabdallah.weather.utils.InternetConnection
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var internetConnection: InternetConnection
    private lateinit var txtInternet:TextView
    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var constraintLayout: CoordinatorLayout


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadSettings()
        setContentView(R.layout.activity_main)

        constraintLayout=findViewById(R.id.main_acti)
        animationDrawable= constraintLayout.background as AnimationDrawable

        animationDrawable.apply {
            setEnterFadeDuration(1000)
            setExitFadeDuration(2000)
            start()
        }

         val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
         val navController =findNavController(R.id.nav_host_fragment)
         bottomNavigationView.setupWithNavController(navController)



        // val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        //val navController = navHostFragment.findNavController() as NavHostController




        txtInternet = findViewById(R.id.txt_internet)
        internetConnection= InternetConnection(this)

        internetConnection.observe(this, androidx.lifecycle.Observer {
            if (!it)
            {
                txtInternet.visibility= View.VISIBLE
                txtInternet.isSelected=true
                txtInternet.text=resources.getString(R.string.no_internet)
            }
            else
            {
                txtInternet.visibility= View.GONE
            }
        })
    }
    private fun setLocale(lng: String) {
        val locale = Locale(lng)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources
            .updateConfiguration(config, baseContext.resources.displayMetrics)
    }

    private fun loadSettings()
    {
        val sp= PreferenceManager.getDefaultSharedPreferences(this)
        val languageKey=sp.getString("language","")
        setLocale(languageKey!!)
        Constant.appDefaultLanguage = languageKey
        Constant.api_lang=languageKey
    }

}