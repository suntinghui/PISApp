package com.lkpower.pis.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v4.app.NotificationCompat
import com.lkpower.pis.R

object NotificationUtil {

    fun showNotification(context: Context, ticker: String, title: String, msg: String, intent: PendingIntent) {
        try {
            var mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, NotificationChannel.DEFAULT_CHANNEL_ID)

            mBuilder.setContentTitle(title)
                    .setContentText(msg)
                    .setTicker(ticker)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher))
                    .setSmallIcon(R.drawable.icon_small)
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setSound(Uri.parse("android.resource://com.lkpower.pis/raw/tip.mp3"))
                    .setContentIntent(intent)

            var mNotificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager.notify(10, mBuilder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}