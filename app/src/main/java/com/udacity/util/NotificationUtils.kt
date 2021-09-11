package com.udacity.util

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.udacity.R

private const val NOTIFICATION_ID = 0

/**
 * Builds and delivers a notification.
 *
 * @param messageBody, notification text.
 * @param applicationContext, activity context.
 */
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {


    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.github_repo_channel_id)
    )
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)

    notify(NOTIFICATION_ID, builder.build())
}