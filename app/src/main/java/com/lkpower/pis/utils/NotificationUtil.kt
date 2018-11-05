package com.lkpower.pis.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import com.lkpower.pis.R
import java.util.*

object NotificationUtil {

    fun showNotification(context: Context, ticker: String, title: String, msg: String, intent: Intent) {

        var notifyId = Random().nextInt()
        // 因为将Notification设置为FLAG_INSISTENT,点击无关关闭，只能记录ID，自己关闭。
        intent.putExtra("NotifyId", notifyId)

        // 把requestCode参数设成了与notificationManager.notify(id,notification)的id一致，这样就可保证每个notification对就一个唯一的Intent
        var pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, notifyId, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        try {
            var mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, NotificationChannel.DEFAULT_CHANNEL_ID)

            mBuilder.setContentTitle(title)
                    .setContentText(msg)
                    .setTicker(ticker)
                    .setStyle(NotificationCompat.BigTextStyle().setBigContentTitle(title).bigText(msg))
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher))
                    .setSmallIcon(R.drawable.icon_small)
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)

            var notification = mBuilder.build()

            // 一直在响，且不可自动取消
            notification.flags = Notification.FLAG_ONGOING_EVENT or Notification.FLAG_INSISTENT

            var mNotificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // id如果相同会覆盖原来的通知
            mNotificationManager.notify(notifyId, notification)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}