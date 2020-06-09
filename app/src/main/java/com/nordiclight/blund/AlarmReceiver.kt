package com.nordiclight.blund

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat


class AlarmReceiver : BroadcastReceiver() {

    fun contentPendingIntent(
        context: Context,
        name: String
    ): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            action = name
        }
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    override fun onReceive(context: Context, intent: Intent) {

        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotification(
            context.getText(R.string.ready).toString(),
            context
        )
        val intent = Intent(context, MyService::class.java)
        if (context != null) {
            context.startService(intent)
        }

    }

}
