package com.nordiclight.blund


import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import android.content.BroadcastReceiver



class AlertDetails : BroadcastReceiver() {
    private val REQUEST_CODE = 0

    override fun onReceive(context: Context, intent: Intent) {

        val intent = Intent(context, MyService::class.java)
        if (context != null) {
            context.stopService(intent)
        }

        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancelAll()
    }

}
