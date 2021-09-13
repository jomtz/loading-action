package com.udacity.util

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.udacity.DetailActivity
import com.udacity.MainActivity
import com.udacity.R

private const val NOTIFICATION_ID = 0

/**
 * Builds and delivers a notification.
 *
 * @param messageBody, notification text.
 * @param applicationContext, activity context.
 */
@SuppressLint("UnspecifiedImmutableFlag")
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context, status: String) {

    // Create the content intent for the notification, which launches the activity
    val contentIntent = Intent(applicationContext, DetailActivity::class.java)
    // Transfer content to DetailActivity
    contentIntent.apply {
        putExtra("fileName", messageBody)
        putExtra("status", status)
    }

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val actionIntent = NotificationCompat.Action.Builder(0,"Show More...",contentPendingIntent).build()

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.github_repo_channel_id)
    )
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        //setAutoCancel true dismisses the notification after the intent
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(actionIntent)

    notify(NOTIFICATION_ID, builder.build())
}

