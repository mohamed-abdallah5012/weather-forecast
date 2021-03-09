package com.mohamedabdallah.weather.ui.activity

import android.content.Context
import android.content.res.Configuration
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
import androidx.lifecycle.LiveData
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
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var internetConnection: InternetConnection
    private lateinit var txt_internet:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadSettings()
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        txt_internet = findViewById(R.id.txt_internet)
        bottomNavigationView.setOnNavigationItemSelectedListener(listener)
        internetConnection= InternetConnection(this)


        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, HomeFragment()).commit()

        internetConnection.observe(this, androidx.lifecycle.Observer {
            if (!it)
            {
                txt_internet.visibility= View.VISIBLE
                txt_internet.isSelected=true
                txt_internet.text=resources.getString(R.string.no_internet)
            }
            else
            {
                txt_internet.visibility= View.GONE

            }
        })
    }

    private val listener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId)
            {
                R.id.bottom_today ->{supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, HomeFragment()).commit()
                }
                R.id.bottom_map -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, MapFragment()).commit()

                R.id.bottom_daily -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, DailyFragment()).commit()

                R.id.bottom_settings -> supportFragmentManager.beginTransaction()
                   .replace(R.id.fragment, SettingsFragment()).commit()


                else -> {}
            }
            true
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