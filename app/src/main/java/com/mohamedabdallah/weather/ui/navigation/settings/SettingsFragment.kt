package com.mohamedabdallah.weather.ui.navigation.settings

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.preference.Preference
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.ui.activity.MainActivity
import com.mohamedabdallah.weather.ui.navigation.alert.AlertFragment
import com.mohamedabdallah.weather.ui.navigation.favorite.FavoriteFragment
import java.util.*


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        darkMode()
        language()
        navigateToNotification()
    }

    private fun darkMode() {
        var darkMode = findPreference<CheckBoxPreference>("background_mode")
        darkMode?.setOnPreferenceChangeListener { prefs, obj ->
            val yes = obj as Boolean
            if (yes) {

            } else {

            }
            true
        }
    }

    private fun language() {
        val language = findPreference<ListPreference>("language")
        language?.setOnPreferenceChangeListener { prefs, obj ->
            val items = obj as String
            if (prefs.key == "language") {
                when (items) {
                    "en" -> setLocale("en")
                    "ar" -> setLocale("ar")
                    "fr" -> setLocale("fr")
                }
                ActivityCompat.recreate(requireActivity())
            }
            true
        }
    }

    private fun setLocale(lng: String) {
        val locale = Locale(lng)
        Locale.setDefault(locale)
        val config = Configuration()
        //config.locales=locale
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }


    private fun navigateToNotification() {

        val notification = findPreference<androidx.preference.Preference>("notification")
        notification?.setOnPreferenceClickListener {
            if (it.key=="notification")
            {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment, AlertFragment())
                    .commit()
            }
            true
        }
    }

}