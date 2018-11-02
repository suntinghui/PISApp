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
import com.umeng.message.entity.UMessage
import org.android.agoo.common.AgooConstants
import org.json.JSONObject
import java.lang.Exception
import java.util.*


/*
Bundle[{task_id=null, id=f__-mSxtk3anNW0E&&uatlbh3154114788036601&&WmxRMeuVKssDAAQme/xemu+B&&01&&, body={"display_type":"notification","extra":{"MissionInstanceId":"0c27f6e4-6d87-474e-85e7-bb6327acf4d3","StationName":"南宁站","PushType":"SetOutBeginWarning","InstanceId":"9c88b677-9f93-4e1e-8d14-297c5d0b3dfc","ArriveDate":"2018-10-28 07:00:00","StationId":"2a6ba24d-4136-44aa-a4ff-efdb6306d0c8"},"msg_id":"uatlbh3154114788036601","body":{"after_open":"go_custom","ticker":"车次T290/89-南宁站 (出乘任务)","builder_id":0,"custom":"comment-notify","text":"车次T290/89-南宁站 出乘任务即将开始：07:00:00","title":"车次T290/89-南宁站 出乘任务即将开始：07:00:00"},"random_min":0}}]
*/

class UMengNotificationService : UmengMessageService() {

    override fun onMessage(context: Context, intent: Intent) {
        try {
            Logger.e("收到推送消息...")

            var ticker = ""
            var title = ""
            var content = ""
            var tempIntent: Intent = Intent()

            val messageBody = intent.getStringExtra(AgooConstants.MESSAGE_BODY)
            Logger.e("MESSAGE_BODY: $messageBody")

            var umessage = UMessage(JSONObject(messageBody))

            var InstanceId = umessage.extra.get("InstanceId") ?: "" // 发车实例
            var StationId = umessage.extra.get("StationId") ?: "" // 站点ID
            var StationName = umessage.extra.get("StationName") ?: "" // 站点名称
            var ArriveDate = umessage.extra.get("ArriveDate") ?: "" // 任务开始时间
            var PushType = umessage.extra.get("PushType") ?: "" // 预警类型

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