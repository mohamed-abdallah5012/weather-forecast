package com.mohamedabdallah.weather.ui.navigation.alert

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.data.favorite.FavoritePlace
import com.mohamedabdallah.weather.ui.navigation.alert.manager.AlertWorker
import com.mohamedabdallah.weather.utils.getDate
import java.util.*
import java.util.concurrent.TimeUnit

class AlertFragment : Fragment() {

    companion object {
        fun newInstance() =
            AlertFragment()
    }

    private lateinit var viewModel: AlertViewModel
    private lateinit var tvNotificationOnOff:TextView
    private lateinit var swNotificationOnOff:Switch
    private lateinit var btCheckEvent:Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.alert_fragment, container, false)
        tvNotificationOnOff=view.findViewById(R.id.tv_notification_on_off)
        swNotificationOnOff=view.findViewById(R.id.sw_notification_on_off)
        btCheckEvent=view.findViewById(R.id.bt_check_events)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlertViewModel::class.java)
        // TODO: Use the ViewModel

        //btCheckEvent.setOnClickListener { openCalender() }
        btCheckEvent.setOnClickListener { iniWorkManager() }

    }
    @SuppressLint("RestrictedApi")
    private fun iniWorkManager()
    {
        /* val myConstraints = Constraints.Builder()
           .setRequiresDeviceIdle(true) //checks whether device should be idle for the WorkRequest to run
           .setRequiresCharging(true) //checks whether device should be charging for the WorkRequest to run
           .setRequiredNetworkType(NetworkType.CONNECTED) //checks whether device should have Network Connection
           .setRequiresBatteryNotLow(true) // checks whether device battery should have a specific level to run the work request
           .setRequiresStorageNotLow(true) // checks whether device storage should have a specific level to run the work request
           .build()


       */

       /* val current=System.currentTimeMillis()
        val fut=1615234503957

        */

        val name = workDataOf(
            "type" to "Clouds",
            "lat" to 66.2511,
            "lng" to 66.2511
        )
        var oneTimeRequest = OneTimeWorkRequestBuilder<AlertWorker>()
            .setInitialDelay(2, TimeUnit.SECONDS)
            .setInputData(name)
        .build()

        //  37.422001
        //-122.0840133         Clouds

        //30.0444196          Clear
        //31.2357116



        WorkManager.getInstance(requireContext()).enqueue(oneTimeRequest)


        WorkManager.getInstance(requireContext()).getWorkInfoByIdLiveData(oneTimeRequest.id)
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                if (it != null ) {
                    Toast.makeText(requireContext(), "Work was success: ${it.state}", Toast.LENGTH_LONG).show()
                    Log.i("TAG", "iniWorkManager: ${it.state}")

                }
            })


       // WorkManager.cancelWorkById(oneTimeRequest.id)


    }

    private fun openCalender(){
        val mCurrentTime = Calendar.getInstance()
        val hour = mCurrentTime[Calendar.HOUR_OF_DAY]
        val minute = mCurrentTime[Calendar.MINUTE]
        val  mTimePicker = TimePickerDialog(
            requireContext(),
            OnTimeSetListener { timePicker: TimePicker?, selectedHour: Int, selectedMinute: Int ->
                btCheckEvent.text="$selectedHour:$selectedMinute"
            },
            hour,
            minute,
            true
        )
        mTimePicker.show()
    }

}