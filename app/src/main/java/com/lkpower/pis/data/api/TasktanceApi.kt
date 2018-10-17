package com.lkpower.pis.data.api

import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.protocol.OutCheckInInfo
import com.lkpower.pis.data.protocol.UserInfo
import com.lkpower.pis.data.protocol.XJ_LCFCInfo
import io.reactivex.Observable
import retrofit2.http.*

interface TasktanceApi {

    // 获取出乘报到任务列表
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetSetOutCheckInList")
    fun getOutCheckInList(@Field("InstanceId") instanceId: String,
                          @Field("tokenKey") tokenKey: String): Observable<BaseResp<List<OutCheckInInfo>>>


    // 获取出乘报到任务实例（详情）
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetSetOutCheckIn")
    fun getOutCheckInDetail(@Field("InstanceId") instanceId: String,
                            @Field("TaskId") taskId: String,
                            @Field("tokenKey") tokenKey: String): Observable<BaseResp<OutCheckInInfo>>

    // 出乘报到
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=SetOutCheckIn")
    fun setOutCheckin(@Field("TaskId") taskId: String,
                      @Field("tokenKey") tokenKey: String): Observable<BaseResp<String>>
}