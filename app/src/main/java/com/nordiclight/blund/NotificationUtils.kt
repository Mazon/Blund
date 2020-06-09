/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nordiclight.blund

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat

//import com.example.android.eggtimernotifications.MainActivity
//import com.example.android.eggtimernotifications.R
//import com.example.android.eggtimernotifications.receiver.SnoozeReceiver

// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

/**
 * Builds and delivers the notification.
 *
 * @param context, activity context.
 */
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    // Create the content intent for the notification, which launches
    // this activity
    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    //add style
    val Image = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.notification_icon
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(BitmapFactory.decodeResource(applicationContext.getResources(), R.mipmap.ic_launcher))
        .bigLargeIcon(null)

    //add snooze action
    val snoozeIntent = Intent(applicationContext, AlertDetails::class.java)
    val snoozePendingIntent: PendingIntent = PendingIntent.getBroadcast(
        applicationContext,
        REQUEST_CODE,
        snoozeIntent,
        FLAGS)

    val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    val soundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    //get an instance of NotificationCompat.Builder
    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_channel_id)
    )
        // use the new 'breakfast' notification channel

        //set title, text and icon to builder
        .setSmallIcon(R.drawable.pillow)
        .setContentTitle(applicationContext
            .getString(R.string.notification_title))
        .setContentText(messageBody)
        //.setDefaults(Notification.DEFAULT_SOUND)
        //.setSound(soundUri)

    //set content intent
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)

        //add style to builder
        .setStyle(bigPicStyle)
        .setLargeIcon(BitmapFactory.decodeResource(applicationContext.getResources(), R.mipmap.ic_launcher))

        //add snooze action
       .addAction(
            R.drawable.pillow,
            applicationContext.getString(R.string.snooze),
            snoozePendingIntent
       )

        // set priority
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    // call notify
    notify(NOTIFICATION_ID, builder.build())
}


/**
 * Cancels all notifications.
 *
 */
fun NotificationManager.cancelNotifications() {
    cancelAll()
}
