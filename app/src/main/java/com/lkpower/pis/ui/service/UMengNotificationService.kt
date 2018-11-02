package com.lkpower.pis.ui.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import com.lkpower.pis.R
import com.orhanobut.logger.Logger
import com.umeng.message.UmengMessageService
import com.umeng.message.entity.UMessage

class UMengNotificationService : UmengMessageService() {

    override fun onMessage(context: Context?, intent: Intent?) {
        Logger.e("收到推送消息...")


    }
}