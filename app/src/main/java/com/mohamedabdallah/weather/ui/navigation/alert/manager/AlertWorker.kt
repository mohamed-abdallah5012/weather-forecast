package com.mohamedabdallah.weather.ui.navigation.alert.manager

import android.R
import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mohamedabdallah.weather.repo.HomeRepository
import com.mohamedabdallah.weather.ui.navigation.home.HomeViewModel
import com.mohamedabdallah.weather.utils.Constant
import kotlinx.coroutines.delay


class AlertWorker(context: Context,workerParams: WorkerParameters) :Worker(context, workerParams){

    private val homeRepository = HomeRepository(Application())
    override fun doWork(): Result {

        // Log.i("TAG", "doWork: $i")
        val type=inputData.getString("type")
        val lat=inputData.getDouble("lat",0.0)
        val lng=inputData.getDouble("lng",0.0)

        val x= homeRepository.getWeatherAlert(lat,lng,type!! )
        if (x)
            {
                Log.i("TAG", "doWork: ssssssssssssssss")
                createNotificationChannels()
                sendOnChannel2(type)
            }
        else
            Log.i("TAG", "doWork: ffffffffffffffffff")

        return Result.success()
    }

    private  fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                "1",
                "Channel 1",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "This is Channel 1"
            val channel2 = NotificationChannel(
                "2",
                "Channel 2",
                NotificationManager.IMPORTANCE_LOW
            )
            channel2.description = "This is Channel 2"
            val manager: NotificationManager =
                applicationContext.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel1)
            manager.createNotificationChannel(channel2)
        }
    }

    private fun sendOnChannel2(message:String) {

        val notificationManager = NotificationManagerCompat.from(applicationContext);
        val notification: Notification = NotificationCompat.Builder(applicationContext, "1")
            .setSmallIcon(R.drawable.sym_def_app_icon)
            .setContentTitle("Weather Status Today is $message ")
            .setContentText("BE CAREFUL")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
        notificationManager.notify(2, notification)
        if (Constant.notification)
        {
            val  player=MediaPlayer.create(applicationContext,com.mohamedabdallah.weather.R.raw.alert)
            player.start()
        }

    }
}