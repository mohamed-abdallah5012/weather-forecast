package com.mohamedabdallah.weather.ui.navigation.alert

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.work.*
import com.dpro.widgets.WeekdaysPicker
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.mohamedabdallah.weather.R
import com.mohamedabdallah.weather.data.alert.AlertResponse
import com.mohamedabdallah.weather.ui.navigation.alert.manager.AlertWorker
import com.mohamedabdallah.weather.utils.Constant
import com.mohamedabdallah.weather.utils.calcTTTTTTTTTTTTTTTTTTTTTTT
import com.mohamedabdallah.weather.utils.getLiveWeekDays
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit

class AddAlertFragment : Fragment() {

    private lateinit var oneTimeRequest: MutableList<UUID>
    private lateinit var selectedTime: Button
    private lateinit var addAlert: Button
    private lateinit var alarmTitle: TextInputEditText
    private lateinit var alarmDates: WeekdaysPicker
    private lateinit var chipsTypes: ChipGroup
    private lateinit var viewModel: AlertViewModel
    private val chipsTypeData =
        listOf("Snow", "Extreme", "Mist", "Thunderstorm", "Rain", "Wind", "Clouds")
    private var selectedType: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_alert, container, false)

        selectedTime = view.findViewById(R.id.bt_selected_time)
        addAlert = view.findViewById(R.id.bt_add_alarm)
        alarmTitle = view.findViewById(R.id.et_alarm_title)
        chipsTypes = view.findViewById(R.id.chips_alarm_types)
        alarmDates = view.findViewById(R.id.add_alarm_days)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlertViewModel::class.java)

        val alertId = arguments?.getLong("alertId")
        val alertTitle = arguments?.getString("alertTitle")
        if (alertId != null && alertTitle != null)
            alarmTitle.setText(alertTitle)

        alarmDates.sundayFirstDay = false
        alarmDates.setCustomDays(getLiveWeekDays())


        initChipsTypes()
        selectedTime.setOnClickListener { openCalender() }
        addAlert.setOnClickListener { addAlarm(alertId) }

    }

    private fun initChipsTypes() {
        for (text in chipsTypeData) {
            val chip = layoutInflater.inflate(R.layout.layout_chip_entry, chipsTypes, false) as Chip
            chip.text = text
            chip.setOnClickListener {
                selectedType = chip.text.toString()
            }
            chipsTypes.addView(chip)
        }
    }

    private fun addAlarm(id: Long?) {

        if (!validateTitle()) {
            showToast("Enter Title")
            return
        }
        if (!validateAlarmType()) {
            showToast("Select Type of Alarm")
            return
        }
        if (!validateDaySelected()) {
            showToast("Select one or more Day")
            return
        }
        setWorker(id)
    }

    private fun validateTitle() = !(alarmTitle.text == null || alarmTitle.text!!.isEmpty())

    private fun validateAlarmType() = chipsTypes.checkedChipId != -1

    private fun validateDaySelected() = alarmDates.selectedDays.size >= 1

    private fun getSelectedDays() = alarmDates.selectedDays

    private fun openCalender() {
        val mCurrentTime = Calendar.getInstance()
        val hour = mCurrentTime[Calendar.HOUR_OF_DAY]
        val minute = mCurrentTime[Calendar.MINUTE]
        val mTimePicker = TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { timePicker: TimePicker?, selectedHour: Int, selectedMinute: Int ->
                selectedTime.text = "$selectedHour:$selectedMinute"
            },
            hour,
            minute,
            true
        )
        mTimePicker.show()
    }

    @SuppressLint("RestrictedApi")
    private fun setWorker(id: Long?) {

        if (id !=null) {
            viewModel.deleteAlertItem(id)
            WorkManager.getInstance(requireContext()).cancelAllWorkByTag(id.toString())
        }
        val selectedDays: List<Int> = getSelectedDays()
        val delayTime = calcTTTTTTTTTTTTTTTTTTTTTTT(getSelectedDays())

        oneTimeRequest = mutableListOf<UUID>()
        val name = workDataOf(
            "type" to selectedType,
            "lat" to Constant.myLat,
            "lng" to Constant.myLon
        )
        val alertId = viewModel.addAlertList(
            AlertResponse(
                oneTimeRequest,
                alarmTitle.text.toString(),
                selectedType!!,
                selectedDays
            )
        )
        for (i in delayTime.indices) {
            var request = OneTimeWorkRequestBuilder<AlertWorker>()
                .setInitialDelay((delayTime[i].toLong() * 86400), TimeUnit.SECONDS)
                .setInputData(name)
                .addTag(alertId.toString())
                .build()
            WorkManager.getInstance(requireContext()).enqueue(request)
            oneTimeRequest.add(request.id)
        }
        view?.findNavController()?.navigate(R.id.alertFragment)
    }

    private fun showToast(message: String) {
        val mToast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG)
        val view = mToast.view
        view?.setBackgroundResource(R.drawable.toast_background)
        mToast.show()

    }
}