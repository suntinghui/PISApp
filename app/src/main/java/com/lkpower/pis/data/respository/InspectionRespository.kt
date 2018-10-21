package com.lkpower.pis.data.respository

import com.lkpower.base.data.net.RetrofitFactory
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.base.ext.convert
import com.lkpower.pis.data.api.DrivingInfoApi
import com.lkpower.pis.data.api.InspectionApi
import com.lkpower.pis.data.api.LearnDocApi
import com.lkpower.pis.data.protocol.*
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Inject

class InspectionRespository @Inject constructor() {

    // 获取当前发车实例对应的站点列表
    fun getSiteList(instanceId: String, tokenKey: String): Observable<BaseResp<List<XJ_CZSL>>> {
        return RetrofitFactory.instance.create(InspectionApi::class.java).getSiteList(instanceId, tokenKey)
    }

    // 获取当前发车实例指定站点对应的巡检任务列表
    fun getXJTaskList(instanceId: String, siteId: String, tokenKey: String): Observable<BaseResp<MissionStateInfo>> {
        return RetrofitFactory.instance.create(InspectionApi::class.java).getXJTaskList(instanceId, siteId, tokenKey)
    }

    // 获取巡检任务实例详细信息
    fun getXJTaskModel(taskId: String, tokenKey: String): Observable<BaseResp<MissionStateInfo>> {
        return RetrofitFactory.instance.create(InspectionApi::class.java).getXJTaskModel(taskId, tokenKey)
    }

    // 更新巡检任务状态
    fun updateMissionInfoExt(taskId: String, tokenKey: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(InspectionApi::class.java).updateMissionInfoExt(taskId, tokenKey)
    }

    // 任务预警触发日志
    fun alarmLogInfo(instanceId: String, deviceId: String, missionInstanceId: String, remark: String, tokenKey: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(InspectionApi::class.java).alarmLogInfo(instanceId, deviceId, missionInstanceId, remark, tokenKey)
    }

    // 任务预警接收回写
    fun alarmUpdateLogInfo(instanceId: String, deviceId: String, missionInstanceId: String, remark: String, tokenKey: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(InspectionApi::class.java).alarmUpdateLogInfo(instanceId, deviceId, missionInstanceId, remark, tokenKey)
    }
}