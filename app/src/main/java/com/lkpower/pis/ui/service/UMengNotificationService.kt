package com.lkpower.pis.ui.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.lkpower.pis.common.AppManager
import com.lkpower.pis.ui.activity.*
import com.lkpower.pis.utils.NotificationUtil
import com.lkpower.pis.utils.ViewUtils
import com.orhanobut.logger.Logger
import com.umeng.message.UmengMessageService
import java.lang.Exception
import java.util.*

class UMengNotificationService : UmengMessageService() {

    override fun onMessage(context: Context, intent: Intent) {
        try {
            Logger.e("收到推送消息...${Gson().to(intent)}")

            var ticker = ""
            var title = ""
            var content = ""
            var tempIntent: Intent = Intent()

            var InstanceId = intent.getStringExtra("InstanceId") ?: "" // 发车实例
            var StationId = intent.getStringExtra("StationId") ?: "" // 站点ID
            var StationName = intent.getStringExtra("StationName") ?: "" // 站点名称
            var ArriveDate = intent.getStringExtra("ArriveDate") ?: "" // 任务开始时间
            var PushType = intent.getStringExtra("PushType") ?: "" // 预警类型

            when (PushType) {
                "MissionWarning" -> { // 到站预警
                    ticker = "您有一条到站预警消息"
                    title = "到站预警"
                    content = "$StationName 即将到站"
                    tempIntent = Intent(context, InspectionTaskListActivity::class.java)
                }

                "SetOutBeginWarning" -> { // 出乘任务开始预警
                    ticker = "您有一条出乘开始预警消息"
                    title = "出乘任务开始预警"
                    content = "$StationName 出乘任务将在$ArriveDate 开始，请及时处理"
                    tempIntent = Intent(context, SetoutCheckinListActivity::class.java)

                }

                "SetOffBeginWarning" -> { // 退乘任务开始预警
                    ticker = "您有一条退乘开始预警消息"
                    title = "退乘任务开始预警"
                    content = "$StationName 退乘任务将在$ArriveDate 开始，请及时处理"
                    tempIntent = Intent(context, SetoffCheckinListActivity::class.java)

                }

                "SetOutCheckInLateWarning" -> { // 出乘报到超时预警
                    ticker = "您有一条出乘报到超时预警消息"
                    title = "出乘报到超时预警"
                    content = "$StationName 出乘报到任务已超时，请及时处理"
                    tempIntent = Intent(context, SetoutCheckinListActivity::class.java)

                }

                "SetOffCheckInLateWarning" -> { // 退乘报到超时预警
                    ticker = "您有一条退乘报到超时预警消息"
                    title = "退乘报到超时预警"
                    content = "$StationName 退乘报到任务已超时，请及时处理"
                    tempIntent = Intent(context, SetoffCheckinListActivity::class.java)

                }

                "SetOutAlcoholTestLateWarning" -> { // 出乘酒测超时预警
                    ticker = "您有一条出乘酒测超时预警消息"
                    title = "出乘酒测超时预警"
                    content = "$StationName 出乘酒测任务已超时，请及时处理"
                    tempIntent = Intent(context, SetoutAlcoholTestListActivity::class.java)

                }

                "SetOffAlcoholTestLateWarning" -> { // 退乘酒测超时预警
                    ticker = "您有一条退乘酒测超时预警消息"
                    title = "退乘酒测超时预警"
                    content = "$StationName 退乘酒测任务已超时，请及时处理"
                    tempIntent = Intent(context, SetoffAlcoholTestListActivity::class.java)

                }

                "SetOutConfirmProjectLateWarning" -> { // 出乘确认项目超时预警
                    ticker = "您有一条出乘确认项目超时预警消息"
                    title = "出乘确认项目超时预警"
                    content = "$StationName 出乘确认项目已超时，请及时处理"
                    tempIntent = Intent(context, SetoutConfirmProjListActivity::class.java)

                }

                "SetOutQuestionLateWarning" -> { // 出乘答题任务超时预警
                    ticker = "您有一条出乘答题任务超时预警消息"
                    title = "出乘答题任务超时预警"
                    content = "$StationName 出乘答题任务已超时，请及时处理"
                    tempIntent = Intent(context, LearnDocListActivity::class.java)

                }

                "SetOutLateWarning" -> { // 出乘最终确认超时预警
                    ticker = "您有一条出乘最终确认超时预警消息"
                    title = "出乘最终确认超时预警"
                    content = "$StationName 出乘最终确认已超时，请及时处理"
                    tempIntent = Intent(context, SetoutListActivity::class.java)

                }

                "SetOffLateWarning" -> { // 退乘最终确认超时预警
                    ticker = "您有一条退乘最终确认超时预警消息"
                    title = "退乘最终确认超时预警"
                    content = "$StationName 退乘最终确认已超时，请及时处理"
                    tempIntent = Intent(context, SetoffListActivity::class.java)
                }

                "TaskConveyWarning" -> { // 计划任务传达预警
                    ticker = "您有一条计划任务传达预警消息"
                    title = "计划任务传达预警"
                    content = "$StationName 计划任务传达将在$ArriveDate 开始，请及时处理"
                    tempIntent = Intent(context, TaskConveyListActivity::class.java)

                }

                "XLCheckWarning" -> { // 巡检任务
                    ticker = "您有一条巡检任务预警消息"
                    title = "巡检任务预警"
                    content = "$StationName 巡检任务将在$ArriveDate 开始，请及时处理"
                    tempIntent = Intent(context, InspectionTaskListActivity::class.java)

                }

                else -> {
                    ViewUtils.error(context, "未知类型${PushType}")
                    return
                }
            }// end When


            tempIntent.putExtra("InstanceId", InstanceId)
            tempIntent.putExtra("StationId", StationId)
            tempIntent.putExtra("StationName", StationName)
            tempIntent.putExtra("ArriveDate", ArriveDate)
            tempIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            var pendingIntent: PendingIntent = PendingIntent.getActivity(context, Random().nextInt(), tempIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            NotificationUtil.showNotification(context, ticker, title, content, pendingIntent)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}