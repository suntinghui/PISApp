package com.lkpower.pis.ui.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.lkpower.pis.common.PushTypeConstant
import com.lkpower.pis.data.api.InspectionApi
import com.lkpower.pis.utils.PISUtil
import com.orhanobut.logger.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class NotificationReceiver : BroadcastReceiver() {

    private var InstanceId = "" // 发车实例
    private var StationId = "" // 站点ID
    private var StationName = "" // 站点名称
    private var ArriveDate = "" // 任务开始时间
    private var MissionInstanceId = "" // 相关任务实例ID
    private var PushType = "" // 预警类型
    private var nofifyId: Int = 0

    private lateinit var context: Context

    override fun onReceive(context: Context, intent: Intent) {

        this.context = context

        InstanceId = intent.getStringExtra("InstanceId")
        StationId = intent.getStringExtra("StationId")
        StationName = intent.getStringExtra("StationName")
        ArriveDate = intent.getStringExtra("ArriveDate")
        MissionInstanceId = intent.getStringExtra("MissionInstanceId")
        PushType = intent.getStringExtra("PushType")
        nofifyId = intent.getIntExtra("NotifyId", 0)

        when (PushType) {
            // 该类型确认成功后再关闭通知
            PushTypeConstant.kMissionWarning -> {
                alarmUpdateLogInfo()
            }
            else -> {
                cancelNotify(context)
            }
        }
    }

    // 任务预警触发日志回写
    private fun alarmUpdateLogInfo() {
        var retrofit = Retrofit.Builder()
                .baseUrl(PISUtil.getServerAddress())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val request = retrofit.create(InspectionApi::class.java)
        val call = request.alarmUpdateLogInfo(InstanceId, PISUtil.getDeviceId(context), StationId, MissionInstanceId, "", PISUtil.getTokenKey())
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                Logger.e("AlarmLogInfo 预警触发日志回写成功！")

                cancelNotify(context)
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Logger.e("AlarmLogInfo 预警触发日志回写失败！")
            }
        })
    }

    private fun cancelNotify(context: Context) {
        try {
            var mNotificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager.cancel(nofifyId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}