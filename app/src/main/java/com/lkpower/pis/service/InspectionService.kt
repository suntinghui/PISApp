package com.lkpower.pis.service

import com.lkpower.pis.data.net.RetrofitFactory
import com.lkpower.pis.data.protocol.BaseResp
import com.lkpower.pis.data.api.InspectionApi
import com.lkpower.pis.data.protocol.MissionStateInfo
import com.lkpower.pis.data.protocol.XJ_CZSL
import io.reactivex.Observable

interface InspectionService {

    // 获取当前发车实例对应的站点列表
    fun getSiteList(instanceId: String, tokenKey: String): Observable<List<XJ_CZSL>>

    // 获取当前发车实例指定站点对应的巡检任务列表
    fun getXJTaskList(instanceId: String, siteId: String, tokenKey: String): Observable<List<MissionStateInfo>>

    // 获取巡检任务实例详细信息
    fun getXJTaskModel(taskId: String, tokenKey: String): Observable<MissionStateInfo>

    // 更新巡检任务状态
    fun updateMissionInfoExt(taskId: String, state:String, remark: String, tokenKey: String): Observable<Boolean>

    // 任务预警触发日志
    fun alarmLogInfo(instanceId: String, deviceId: String, missionInstanceId: String, remark: String, tokenKey: String): Observable<Boolean>

    // 任务预警接收回写
    fun alarmUpdateLogInfo(instanceId: String, deviceId: String, missionInstanceId: String, remark: String, tokenKey: String): Observable<Boolean>

}