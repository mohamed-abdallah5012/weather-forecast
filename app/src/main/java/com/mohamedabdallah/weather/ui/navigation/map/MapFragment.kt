package com.mohamedabdallah.weather.ui.navigation.map

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mohamedabdallah.weather.R


class MapFragment : Fragment() {

    companion object {
        fun newInstance() =
            MapFragment()
    }

    private lateinit var viewModel: MapViewModel
    private lateinit var webView: WebView
    private lateinit var fab: FloatingActionButton
    private val ID = "109eb1fce9d8083508804d3fd2ca8104db07af6e"
    private val mapType = arrayOf("aa", "a", "a", "aa", "a")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.map_fragment, container, false)

        webView = view.findViewById(R.id.mapView)
        fab = view.findViewById(R.id.fab)

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)

        fab.setOnClickListener {chooseLayer()}
        initMap()
    }

    private fun initMap(layer:String="temp") {

        webView.webViewClient = WebViewClient()
        webView.loadUrl("http://maps.goweatherradar.com/en/widget/1bfe4f546eb8a1d9fbe2f73812e60361e616c57d?lat=20&lng=50&overlay=${layer}&application_id=XEBIYYivwDIjUX5YHiUSPM19SEOx7fsF")
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
    }

    private fun chooseLayer() {
        val choices = arrayOf("temp", "rain", "wind","pressure","clouds","humidity")
        val alertDialogBuilder = AlertDialog.Builder(context)
            .setTitle("Tilte")
            .setSingleChoiceItems(choices, -1)
            {
                    dialogInterface, i ->
                dialogInterface.cancel()
                initMap(choices[i])
            }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}