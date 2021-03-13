package com.mohamedabdallah.weather.ui.navigation.map

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.utils.Constant


class MapFragment : Fragment() {


    private lateinit var viewModel: MapViewModel
    private lateinit var webView: WebView
    private lateinit var fab: FloatingActionButton
    private lateinit var progressBar: ProgressBar
    private lateinit var chipsModes: ChipGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.map_fragment, container, false)
        webView = view.findViewById(R.id.mapView)
        fab = view.findViewById(R.id.fab)
        chipsModes = view.findViewById(R.id.chips_map_modes)
        progressBar = view.findViewById(R.id.progress_map)


        return view
    }
    private fun initializeDialog() {
        val inflater = LayoutInflater.from(requireContext())
        val inflate_view: View =
            inflater.inflate(R.layout.dialog_contact_dark, null)
        val closeImage =
            inflate_view.findViewById<ImageView>(R.id.bt_close)

        closeImage.setOnClickListener { v: View? ->

        }
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(inflate_view)
        builder.setCancelable(true)
        builder.create()
        builder.create().show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)

        fab.setOnClickListener { showAlertDialog()}
        showProgressBar()
        initMap()
        initMapModes()

    }
    private fun showProgressBar() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it == true)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.VISIBLE
        })
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun initMap() {
        viewModel.currentMode.observe(viewLifecycleOwner, Observer {
            webView.webViewClient = WebViewClient()
            webView.loadUrl("${Constant.radarUrl}${Constant.radarUrlExtension}?lat=${Constant.currentLat}&lng=${Constant.currentLon}&overlay=${it}&application_id=${Constant.radarAppID}")
            webView.settings.setSupportZoom(true)
            webView.settings.javaScriptEnabled = true
        })
    }
    private fun initMapModes() {
        for (text in  viewModel.getAvailableModes()) {
            val chip = layoutInflater.inflate(R.layout.layout_chip_entry, chipsModes, false) as Chip
            chip.text = text
            chip.setOnClickListener {
                viewModel.setCurrentMode(chip.text.toString())
                initMap()
            }
            chipsModes.addView(chip)
        }
    }
    private fun showAlertDialog() {
        val choices = viewModel.getAvailableModes()
        val alertDialogBuilder = AlertDialog.Builder(context)
            .setTitle(resources.getString(R.string.choose_mode))
            .setSingleChoiceItems(choices, -1)
            { dialogInterface, i ->
                dialogInterface.cancel()
                viewModel.setCurrentMode(choices[i])
                Log.i("TAG", "showAlertDialog: ${choices[i]}")
                initMap()
            }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}