package com.lkpower.pis.data.api

import com.lkpower.pis.data.protocol.BaseResp
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.protocol.PublishInfo
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/*
段发信息
 */
interface PublishApi {

    // 发信息查询列表
    @FormUrlEncoded
    @POST("InfoManager.ashx?Commond=GetInfoPublishList")
    fun getPublishInfoList(@Field("searchInfo") searchInfo: String,
                           @Field("pageInfo") pageInfo: String,
                           @Field("tokenKey") tokenKey: String): Observable<BaseResp<ListResult<PublishInfo>>>

    // 段发信息明细
    @FormUrlEncoded
    @POST("InfoManager.ashx?Commond=GeXJ_InfoPublishModel")
    fun getPublishInfoModel(@Field("ID") id: String,
                            @Field("DeviceId") deviceId: String,
                            @Field("tokenKey") tokenKey: String): Observable<BaseResp<PublishInfo>>

}