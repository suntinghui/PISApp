package com.lkpower.pis.data.api

import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.protocol.UserInfo
import com.lkpower.pis.data.protocol.XJ_LCFC
import io.reactivex.Observable
import retrofit2.http.*

interface UserApi {

    // 登录
    @FormUrlEncoded
    @POST("Identity.ashx?Commond=AppLogin")
    fun login(@Field("loginName") loginName: String,
              @Field("loginPwd") loginPwd: String,
              @Field("deviceId") deviceId: String): Observable<BaseResp<UserInfo>>


    // 注销
    @FormUrlEncoded
    @POST("Identity.ashx?Commond=LoginOff")
    fun loginOff(@Field("tokenKey") tokenKey: String): Observable<BaseResp<String>>

    // 修改密码
    @FormUrlEncoded
    @POST("Identity.ashx?Commond=ChangePwd")
    fun changePwd(@Field("LoginName") loginName: String,
              @Field("HisPwd") loginPwd: String,
              @Field("newPwd") newPwd: String,
              @Field("tokenKey") tokenKey: String): Observable<BaseResp<String>>


    // 获取当前人员的发车实例
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetLCFCInstance")
    fun getLCFCInstance(@Field("EmpId") empId: String,
                        @Field("tokenKey") tokenKey: String): Observable<BaseResp<List<XJ_LCFC>>>
}