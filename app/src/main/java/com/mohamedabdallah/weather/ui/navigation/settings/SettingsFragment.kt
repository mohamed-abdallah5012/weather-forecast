package com.mohamedabdallah.weather.ui.navigation.settings

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.mohamedabdallah.weather.R
import java.util.*


class SettingsFragment : PreferenceFragmentCompat() {

    private var alert: AlertDialog? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val view:View?=parentFragment?.view
        view?.setBackgroundColor(R.drawable.back_list)

        darkMode()
        language()
        navigateToNotification()
        aboutMe()
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
                view?.findNavController()?.navigate(R.id.alertFragment)
            }
            true
        }
    }

    private fun aboutMe()
    {
        val notification = findPreference<androidx.preference.Preference>("about")
        notification?.setOnPreferenceClickListener {
            if (it.key=="about")
            {
                openDialogue()
                Log.i("TAG", "aboutMe: ddddddddddddddddddddddddddddddddddddddddddddddd")
            }
            Log.i("TAG", "aboutMe: hjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj")

            true
        }
    }

    private fun openDialogue() {
        val inflater = LayoutInflater.from(context)
        val inflate_view = inflater.inflate(R.layout.dialog_contact_dark, null)
        val btn_close = inflate_view.findViewById<ImageButton>(R.id.bt_close)
        val facebook =
            inflate_view.findViewById<ImageView>(R.id.facebooky)
        val gmail =
            inflate_view.findViewById<ImageView>(R.id.gmaily)
        val linkedIn =
            inflate_view.findViewById<ImageView>(R.id.linkediny)
        btn_close.setOnClickListener { v: View? ->
            alert?.dismiss() }
        facebook.setOnClickListener { v: View? -> openFacebook() }
        linkedIn.setOnClickListener { v: View? -> openLinkedIn() }
        gmail.setOnClickListener { v: View? -> sendMail() }
        val builder = AlertDialog.Builder(context)
        builder.setView(inflate_view)
        builder.setCancelable(false)
        alert = builder.create()
        alert?.show()
    }
    private fun openFacebook() {
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100006851673761"));
//        startActivity(browserIntent);
        val profile_url = "https://www.facebook.com/profile.php?id=100047805403025"
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(profile_url))
            intent.setPackage("com.facebook.android")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            //if the user doesn't have a LinkedIn account, open an intent to a browser
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(profile_url)))
        }
    }

    private fun openLinkedIn() {
        val profile_url = "https://www.linkedin.com/in/mohamed-abdallah-092973b0/"
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(profile_url))
            intent.setPackage("com.linkedin.android")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            //if the user doesn't have a LinkedIn account, open an intent to a browser
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(profile_url)))
        }
    }

    private fun sendMail() {
        val recipientList = "mohamed.abdallah8882@gmail.com"
        val recipients =
            recipientList.split(",".toRegex()).toTypedArray()
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, recipients)
        intent.type = "message/rfc822"
        try {
            startActivity(Intent.createChooser(intent, "Pick an Email Client !"))
        } catch (e: ActivityNotFoundException) {
            Log.i("Tag", "sendMail: error")
        }
    }

}