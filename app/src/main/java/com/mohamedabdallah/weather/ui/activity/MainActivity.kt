package com.mohamedabdallah.weather.ui.activity

import com.mohamedabdallah.weather.ui.navigation.daily.DailyFragment
import com.mohamedabdallah.weather.ui.navigation.home.HomeFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.ui.navigation.map.MapFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(listener)


        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, HomeFragment()).commit()


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

                else -> {}
            }
            true
        }
}