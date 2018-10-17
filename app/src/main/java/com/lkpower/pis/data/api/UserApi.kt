package com.lkpower.pis.data.api

import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.protocol.UserInfo
import com.lkpower.pis.data.protocol.XJ_LCFCInfo
import io.reactivex.Observable
import retrofit2.http.*

interface UserApi {

    // 登录
    @FormUrlEncoded
    @POST("Identity.ashx?Commond=AppLogin")
    fun login(@Field("loginName") loginName: String,
              @Field("loginPwd") loginPwd: String,
              @Field("deviceId") deviceId: String): Observable<BaseResp<UserInfo>>


    // 获取当前人员的发车实例
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetLCFCInstance")
    fun getLCFCInstance(@Field("EmpId") empId: String,
                        @Field("tokenKey") tokenKey: String): Observable<BaseResp<List<XJ_LCFCInfo>>>
}