package com.lkpower.pis.data.api

import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.FaultInfo
import com.lkpower.pis.data.protocol.ListResult
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/*
故障信息
 */
interface FaultInfoApi {

    // 故障信息查询列表
    @FormUrlEncoded
    @POST("InfoManager.ashx?Commond=GetFaultInfoList")
    fun getFaultInfoList(@Field("searchInfo") searchInfo: String,
                         @Field("pageInfo") pageInfo: String,
                         @Field("tokenKey") tokenKey: String): Observable<BaseResp<ListResult<FaultInfo>>>

    // 获取故障信息详细信息
    @FormUrlEncoded
    @POST("InfoManager.ashx?Commond=GetFaultInfoModel")
    fun getFaultInfoModel(@Field("FaultId") faultId: String,
                            @Field("tokenKey") tokenKey: String): Observable<BaseResp<FaultInfo>>

    // 故障反馈
    @FormUrlEncoded
    @POST("InfoManager.ashx?Commond=AddFaultInfo")
    fun addFaultInfo(@Field("tokenKey") tokenKey: String,
                     @Field("faultInfo") faultInfo: String): Observable<BaseResp<String>>

    // 故障修复确认
    @FormUrlEncoded
    @POST("InfoManager.ashx?Commond=AddFaultInfoConfirm")
    fun addFaultInfoConfirm(@Field("tokenKey") tokenKey: String,
                            @Field("confirmInfo") confirmInfo: String): Observable<BaseResp<String>>
}