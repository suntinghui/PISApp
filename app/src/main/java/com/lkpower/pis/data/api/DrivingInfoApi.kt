package com.lkpower.pis.data.api

import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.ListResult
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/*
行车信息
 */
interface DrivingInfoApi {

    // 行车信息查询列表
    @FormUrlEncoded
    @POST("InfoManager.ashx?Commond=GetXJ_DrivingInfoList")
    fun getDrivingInfoList(@Field("searchInfo") searchInfo: String,
                           @Field("pageInfo") pageInfo: String,
                           @Field("tokenKey") tokenKey: String): Observable<BaseResp<ListResult<DrivingInfo>>>

    // 获取行车信息详细信息
    @FormUrlEncoded
    @POST("InfoManager.ashx?Commond=GetXJ_DrivingInfoModel")
    fun getDrivingInfoModel(@Field("ID") id: String,
                            @Field("tokenKey") tokenKey: String): Observable<BaseResp<DrivingInfo>>

    // 行车信息上报
    @FormUrlEncoded
    @POST("InfoManager.ashx?Commond=UpdateDrivingInfo")
    fun setLearnDocRead(@Field("InstanceId") instanceId: String,
                        @Field("Remark") remark: String,
                        @Field("tokenKey") tokenKey: String): Observable<BaseResp<String>>
}