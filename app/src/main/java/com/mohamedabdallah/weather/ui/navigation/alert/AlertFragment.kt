package com.mohamedabdallah.weather.ui.navigation.alert

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.data.alert.AlertResponse
import com.mohamedabdallah.weather.ui.navigation.alert.adapter.AlertAdapter
import com.mohamedabdallah.weather.ui.navigation.alert.manager.GovernmentWorker
import com.mohamedabdallah.weather.utils.Constant
import java.util.concurrent.TimeUnit

class AlertFragment : Fragment(), AlertAdapter.OnAlarmlistener {

    companion object {
        fun newInstance() =
            AlertFragment()
    }

    private lateinit var viewModel: AlertViewModel
    private lateinit var tvNotificationOnOff: TextView
    private lateinit var swNotificationOnOff: Switch
    private lateinit var swGovernmentOnOff: Switch
    private lateinit var btCheckEvent: Button
    private lateinit var toAddFragment: FloatingActionButton
    private lateinit var radioGroup: RadioGroup
    private var alertListAdapter = AlertAdapter(emptyList(), this)
    private lateinit var alertRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.alert_fragment, container, false)
        tvNotificationOnOff = view.findViewById(R.id.tv_notification_on_off)
        swNotificationOnOff = view.findViewById(R.id.sw_notification_on_off)
        swGovernmentOnOff = view.findViewById(R.id.sw_government_on_off)
        toAddFragment = view.findViewById(R.id.fab_to_add_alert)
        alertRecyclerView = view.findViewById(R.id.alert_recycler_view)
        radioGroup = view.findViewById(R.id.rg_notification_type)

        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val checkedRadioButton =
                radioGroup.findViewById(radioGroup.checkedRadioButtonId) as RadioButton
            val isChecked = checkedRadioButton.isChecked
            if (isChecked) {
                if (checkedRadioButton==radioGroup.findViewById(R.id.with_sound)) {

                   Constant.notification=true
                }
                if (checkedRadioButton==radioGroup.findViewById(R.id.without_sound)) {
                    Constant.notification=false
                }
            }
        }

        return view
    }

    @SuppressLint("ResourceType")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlertViewModel::class.java)

        swGovernmentOnOff.setOnClickListener(View.OnClickListener {
            if (swGovernmentOnOff.isChecked) {
                iniWorkManager()

            } else {
                WorkManager.getInstance(requireContext()).cancelAllWorkByTag("government")
            }
        })
        toAddFragment.setOnClickListener {
                it.findNavController().navigate(R.id.addAlertFragment)
        }

        alertRecyclerView.adapter = alertListAdapter
        alertRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        alertRecyclerView.setHasFixedSize(true)


        viewModel.getAlertList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            alertListAdapter.setData(it)
            if (it.isNotEmpty())
            alertRecyclerView.visibility=View.VISIBLE
            if (it.isNullOrEmpty())
                alertRecyclerView.visibility=View.GONE

        })
    }

    @SuppressLint("RestrictedApi")
    private fun iniWorkManager() {
        val myConstraints = Constraints.Builder()
            .setRequiresDeviceIdle(true) //checks whether device should be idle for the WorkRequest to run
            .setRequiredNetworkType(NetworkType.CONNECTED) //checks whether device should have Network Connection
            .setRequiresBatteryNotLow(true) // checks whether device battery should have a specific level to run the work request
            .setRequiresStorageNotLow(true) // checks whether device storage should have a specific level to run the work request
            .build()

        val request=PeriodicWorkRequestBuilder<GovernmentWorker>(
            1, TimeUnit.DAYS, // repeatInterval (the period cycle)
            15, TimeUnit.MINUTES) // flexInterval
            .setConstraints(myConstraints)
            .addTag("government")
            .build()

        WorkManager.getInstance(requireContext()).enqueue(request)
    }

    override fun onRemoveAlarmClick(id: Long) {
        viewModel.deleteAlertItem(id)
        WorkManager.getInstance(requireContext()).cancelAllWorkByTag(id.toString())
    }

    override fun onEditAlarmClick(alert: AlertResponse) {

        Log.i("TAG", "onEditAlarmClick: edit")
        val alertTitle=alert.alertTitle
        val alertId=alert.id

        val bundle=Bundle()
        bundle.putString("alertTitle",alertTitle)
        bundle.putLong("alertId",alertId)
        view?.findNavController()?.navigate(R.id.addAlertFragment,bundle)
    }

    private fun notificationMode()
    {
        val  player= MediaPlayer.create(context, R.raw.alert)
        player.start()
    }

}