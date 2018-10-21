package com.lkpower.pis.data.api

import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.EmergencyInfo
import com.lkpower.pis.data.protocol.ListResult
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/*
应急信息
 */
interface EmergencyInfoApi {

    // 应急信息查询列表
    @FormUrlEncoded
    @POST("InfoManager.ashx?Commond=GetEmergencyInfoList")
    fun getEmergencyInfoList(@Field("searchInfo") searchInfo: String,
                             @Field("pageInfo") pageInfo: String,
                             @Field("tokenKey") tokenKey: String): Observable<BaseResp<ListResult<EmergencyInfo>>>

    // 应急处置信息明细
    @FormUrlEncoded
    @POST("InfoManager.ashx?Commond=GetEmergencyInfoModel")
    fun getEmergencyInfoModel(@Field("EmInfoId") emInfoId: String,
                              @Field("tokenKey") tokenKey: String): Observable<BaseResp<EmergencyInfo>>

    // 应急处置反馈
    @FormUrlEncoded
    @POST("InfoManager.ashx?Commond=AddEmergencyInfo")
    fun addEmergencyInfo(@Field("InstanceId") instanceId: String,
                         @Field("Remark") remark: String,
                         @Field("tokenKey") tokenKey: String): Observable<BaseResp<String>>
}