package com.lkpower.pis.data.api

import com.lkpower.pis.data.protocol.BaseResp
import com.lkpower.pis.data.protocol.MissionStateInfo
import com.lkpower.pis.data.protocol.XJ_CZSL
import com.lkpower.pis.data.protocol.XJ_LCFC
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/*
巡检任务
 */

interface InspectionApi {

    // 获取当前发车实例对应的站点列表
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetSiteList")
    fun getSiteList(@Field("InstanceId") instanceId: String,
                    @Field("tokenKey") tokenKey: String): Observable<BaseResp<List<XJ_CZSL>>>

    // 获取当前发车实例指定站点对应的巡检任务列表
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetXJTaskList")
    fun getXJTaskList(@Field("InstanceId") instanceId: String,
                      @Field("SiteId") siteId: String,
                      @Field("tokenKey") tokenKey: String): Observable<BaseResp<List<MissionStateInfo>>>

    // 获取巡检任务实例详细信息
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetXJTaskModel")
    fun getXJTaskModel(@Field("TaskId") taskId: String,
                       @Field("tokenKey") tokenKey: String): Observable<BaseResp<MissionStateInfo>>

    // 更新巡检任务状态
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=UpdateMissionInfoExt")
    fun updateMissionInfoExt(@Field("TaskId") taskId: String,
                             @Field("State") state: String,
                             @Field("Remark") remark: String,
                             @Field("tokenKey") tokenKey: String): Observable<BaseResp<Boolean>>

    // 任务预警触发日志
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=AlarmLogInfo")
    fun alarmLogInfo(@Field("InstanceId") instanceId: String,
                     @Field("DeviceId") deviceId: String,
                     @Field("StationId") stationId: String,
                     @Field("MissionInstanceId") missionInstanceId: String,
                     @Field("Remark") remark: String,
                     @Field("tokenKey") tokenKey: String): Call<BaseResp<Boolean>>

    // 任务预警接收回写
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=AlarmUpdateLogInfo")
    fun alarmUpdateLogInfo(@Field("InstanceId") instanceId: String,
                           @Field("DeviceId") deviceId: String,
                           @Field("StationId") stationId: String,
                           @Field("MissionInstanceId") missionInstanceId: String,
                           @Field("Remark") remark: String,
                           @Field("tokenKey") tokenKey: String): Call<BaseResp<Boolean>>

}