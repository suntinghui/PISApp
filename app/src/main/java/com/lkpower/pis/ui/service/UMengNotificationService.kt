package com.lkpower.pis.ui.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.lkpower.pis.common.AppManager
import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.common.PushTypeConstant
import com.lkpower.pis.data.api.InspectionApi
import com.lkpower.pis.data.protocol.BaseResp
import com.lkpower.pis.ui.activity.*
import com.lkpower.pis.ui.receiver.NotificationReceiver
import com.lkpower.pis.utils.NotificationUtil
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.utils.ViewUtils
import com.orhanobut.logger.Logger
import com.umeng.message.UmengMessageService
import com.umeng.message.entity.UMessage
import org.android.agoo.common.AgooConstants
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class UMengNotificationService : UmengMessageService() {

    private var InstanceId = ""
    private var StationId = ""
    private var StationName = ""
    private var MissionInstanceId = ""
    private var ArriveDate = ""
    private var PushType = ""

    private lateinit var context: Context

    override fun onMessage(ctx: Context, intent: Intent) {
        context = ctx

        try {
            Logger.e("收到推送消息...")

            var ticker = ""
            var title = ""
            var content = ""
            var action = "" // ARouter路径
            var tempIntent: Intent = Intent()

            val messageBody = intent.getStringExtra(AgooConstants.MESSAGE_BODY)
            Logger.e("MESSAGE_BODY: $messageBody")

            var umessage = UMessage(JSONObject(messageBody))

            InstanceId = umessage.extra?.get("InstanceId") ?: "" // 发车实例
            StationId = umessage.extra?.get("StationId") ?: "" // 站点ID
            StationName = umessage.extra?.get("StationName") ?: "" // 站点名称
            MissionInstanceId = umessage.extra?.get("MissionInstanceId") ?: ""// 相关任务实例ID
            ArriveDate = umessage.extra?.get("ArriveDate") ?: "" // 任务开始时间
            PushType = umessage.extra?.get("PushType") ?: "" // 预警类型

            when (PushType) {
                PushTypeConstant.kMissionWarning -> { // 到站预警
                    ticker = "您有一条到站预警消息"
                    title = "到站预警"
                    content = "$StationName 即将到站"
                    action = "/pis/InspectionStationListActivity"
                    tempIntent = Intent(context, InspectionStationListActivity::class.java)

                    AlarmLogInfo()
                }

                PushTypeConstant.kSetOutBeginWarning -> { // 出乘任务开始预警
                    ticker = "您有一条出乘开始预警消息"
                    title = "出乘任务开始预警"
                    content = "$StationName 出乘任务将在$ArriveDate 开始，请及时处理"
                    action = "/pis/SetoutCheckinListActivity"
                    tempIntent = Intent(context, SetoutCheckinListActivity::class.java)

                }

                PushTypeConstant.kSetOffBeginWarning -> { // 退乘任务开始预警
                    ticker = "您有一条退乘开始预警消息"
                    title = "退乘任务开始预警"
                    content = "$StationName 退乘任务将在$ArriveDate 开始，请及时处理"
                    action = "/pis/SetoffCheckinListActivity"
                    tempIntent = Intent(context, SetoffCheckinListActivity::class.java)

                }

                PushTypeConstant.kSetOutCheckInLateWarning -> { // 出乘报到超时预警
                    ticker = "您有一条出乘报到超时预警消息"
                    title = "出乘报到超时预警"
                    content = "$StationName 出乘报到任务已超时，请及时处理"
                    action = "/pis/SetoutCheckinListActivity"
                    tempIntent = Intent(context, SetoutCheckinListActivity::class.java)

                }

                PushTypeConstant.kSetOffCheckInLateWarning -> { // 退乘报到超时预警
                    ticker = "您有一条退乘报到超时预警消息"
                    title = "退乘报到超时预警"
                    content = "$StationName 退乘报到任务已超时，请及时处理"
                    action = "/pis/SetoffCheckinListActivity"
                    tempIntent = Intent(context, SetoffCheckinListActivity::class.java)

                }

                PushTypeConstant.kSetOutAlcoholTestLateWarning -> { // 出乘酒测超时预警
                    ticker = "您有一条出乘酒测超时预警消息"
                    title = "出乘酒测超时预警"
                    content = "$StationName 出乘酒测任务已超时，请及时处理"
                    action = "/pis/SetoutAlcoholTestListActivity"
                    tempIntent = Intent(context, SetoutAlcoholTestListActivity::class.java)

                }

                PushTypeConstant.kSetOffAlcoholTestLateWarning -> { // 退乘酒测超时预警
                    ticker = "您有一条退乘酒测超时预警消息"
                    title = "退乘酒测超时预警"
                    content = "$StationName 退乘酒测任务已超时，请及时处理"
                    action = "/pis/SetoffAlcoholTestListActivity"
                    tempIntent = Intent(context, SetoffAlcoholTestListActivity::class.java)

                }

                PushTypeConstant.kSetOutConfirmProjectLateWarning -> { // 出乘确认项目超时预警
                    ticker = "您有一条出乘确认项目超时预警消息"
                    title = "出乘确认项目超时预警"
                    content = "$StationName 出乘确认项目已超时，请及时处理"
                    action = "/pis/SetoutGroupTaskListActivity"
                    tempIntent = Intent(context, SetoutGroupTaskListActivity::class.java)

                }

                PushTypeConstant.kSetOutQuestionLateWarning -> { // 出乘答题任务超时预警
                    ticker = "您有一条出乘答题任务超时预警消息"
                    title = "出乘答题任务超时预警"
                    content = "$StationName 出乘答题任务已超时，请及时处理"
                    action = "/pis/LearnDocListActivity"
                    tempIntent = Intent(context, LearnDocListActivity::class.java)

                }

                PushTypeConstant.kSetOutLateWarning -> { // 出乘最终确认超时预警
                    ticker = "您有一条出乘最终确认超时预警消息"
                    title = "出乘最终确认超时预警"
                    content = "$StationName 出乘最终确认已超时，请及时处理"
                    action = "/pis/SetoutListActivity"
                    tempIntent = Intent(context, SetoutListActivity::class.java)

                }

                PushTypeConstant.kSetOffLateWarning -> { // 退乘最终确认超时预警
                    ticker = "您有一条退乘最终确认超时预警消息"
                    title = "退乘最终确认超时预警"
                    content = "$StationName 退乘最终确认已超时，请及时处理"
                    action = "/pis/SetoffListActivity"
                    tempIntent = Intent(context, SetoffListActivity::class.java)
                }

                PushTypeConstant.kTaskConveyWarning -> { // 计划任务传达预警
                    ticker = "您有一条计划任务传达预警消息"
                    title = "计划任务传达预警"
                    content = "$StationName 计划任务传达将在$ArriveDate 开始，请及时处理"
                    action = "/pis/TaskConveyListActivity"
                    tempIntent = Intent(context, TaskConveyListActivity::class.java)

                }

                PushTypeConstant.kXLCheckWarning -> { // 巡检任务
                    ticker = "您有一条巡检任务预警消息"
                    title = "巡检任务预警"
                    content = "$StationName 巡检任务将在$ArriveDate 开始，请及时处理"
                    action = "/pis/InspectionStationListActivity"
                    tempIntent = Intent(context, InspectionStationListActivity::class.java)

                }

                PushTypeConstant.kPublish -> { // 段发信息
                    ticker = "收到段发消息"
                    title = "段发消息提醒"
                    content = "收到一条段发信息,请及时查看"
                    action = "/pis/PublishListActivity"
                    tempIntent = Intent(context, PublishListActivity::class.java)
                }

                PushTypeConstant.kPublish -> { // 列车晚点通知
                    ticker = "列车晚点通知"
                    title = "列车晚点提醒"
                    content = "列车已晚点,请知悉"
                    action = "/pis/InspectionStationListActivity"
                    tempIntent = Intent(context, InspectionStationListActivity::class.java)
                }

                PushTypeConstant.kPublish -> { // 列车正点通知
                    ticker = "列车正点通知"
                    title = "列车正点提醒"
                    content = "列车已正点运行,请知悉"
                    action = "/pis/InspectionStationListActivity"
                    tempIntent = Intent(context, InspectionStationListActivity::class.java)
                }

                PushTypeConstant.kPublish -> { // 学习文件信息通知
                    ticker = "学习文件信息通知"
                    title = "学习文件信息通知"
                    content = "收到一条学习文件信息,点击查看"
                    action = "/pis/LearnDocListActivity"
                    tempIntent = Intent(context, LearnDocListActivity::class.java)
                }

                else -> {
                    ViewUtils.error(context, "推送类型：${PushType}")

                    ticker = "收到一条推送消息"
                    title = "巡检任务预警"
                    content = "收到一条乘务巡检消息，请您及时处理，否则有可能会上报后台系统"
                    action = "/pis/LoginActivity"
                    tempIntent = Intent(context, LoginActivity::class.java)
                }
            }// end When


            // 在这里不能直接指定activity,需要去Receiver中处理。
            tempIntent = Intent(context, NotificationReceiver::class.java)

            tempIntent.putExtra("PushType", PushType)
            tempIntent.putExtra("InstanceId", InstanceId)
            tempIntent.putExtra("StationId", StationId)
            tempIntent.putExtra("StationName", StationName)
            tempIntent.putExtra("MissionInstanceId", MissionInstanceId)
            tempIntent.putExtra("ArriveDate", ArriveDate)
            tempIntent.putExtra("action", action)

            NotificationUtil.showNotification(context, ticker, title, content, tempIntent)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 任务预警触发日志
    private fun AlarmLogInfo() {
        var retrofit = Retrofit.Builder()
                .baseUrl(PISUtil.getServerAddress())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val request = retrofit.create(InspectionApi::class.java)
        val call = request.alarmLogInfo(InstanceId, PISUtil.getDeviceId(context), StationId, MissionInstanceId, "", PISUtil.getTokenKey())
        call.enqueue(object : Callback<BaseResp<Boolean>> {
            override fun onResponse(call: Call<BaseResp<Boolean>>, response: Response<BaseResp<Boolean>>) {
                Logger.e("AlarmLogInfo ${response.body().toString()}")
            }

            override fun onFailure(call: Call<BaseResp<Boolean>>, t: Throwable) {
                Logger.e("AlarmLogInfo 预警触发日志失败！")
            }
        })
    }

}