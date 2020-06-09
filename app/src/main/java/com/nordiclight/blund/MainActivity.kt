package com.nordiclight.blund

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    var alarmManager: AlarmManager? = null
    private var alarmMgr: AlarmManager? = null
    var pendingIntent: PendingIntent? = null
    private lateinit var alarmIntent: PendingIntent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //alarmMgr?.cancel(pendingIntent);
        // call create channel
        createChannel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_channel_name)
        )

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val btnShow = findViewById<ImageButton>(R.id.myImageButton)
        btnShow.setOnClickListener { createAlarm(this@MainActivity)
            //alarmMgr?.cancel(pendingIntent);
        }

    }

    fun createAlarm(context: Context) {

        val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }

        val rightNow = Calendar.getInstance()
        val wakeuptime = rightNow.timeInMillis + 60 * 1000 * 480
        val netDate = Date(wakeuptime.toLong())
        val sdf = SimpleDateFormat("HH:mm")
        val waketext = sdf.format(netDate)

        Toast.makeText(context, "Will wake you at: " + waketext.toString(), Toast.LENGTH_LONG).show()

        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmMgr?.set(
            AlarmManager.RTC_WAKEUP,
            wakeuptime,
            alarmIntent
        )

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        var CHANNEL_ID = "blund_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_MAX
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createChannel(channelId: String, channelName: String) {
        // START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                //change importance
                NotificationManager.IMPORTANCE_HIGH
            )//disable badges for this channel
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.notification_channel_description)


            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

        }
        // END create a channel
    }

}


