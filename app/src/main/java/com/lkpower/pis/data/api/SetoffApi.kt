package com.lkpower.pis.data.api

import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.protocol.Setoff
import com.lkpower.pis.data.protocol.SetoffAlcoholTest
import com.lkpower.pis.data.protocol.SetoffCheckIn
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/*
退乘相关
 */
interface SetoffApi {

    // 获取退乘报到任务列表
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetSetOffCheckInList")
    fun getSetoffCheckInList(@Field("InstanceId") instanceId: String,
                             @Field("tokenKey") tokenKey: String): Observable<BaseResp<List<SetoffCheckIn>>>

    // 获取退乘报到任务实例
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetSetOffCheckIn")
    fun getSetoffCheckIn(@Field("InstanceId") instanceId: String,
                         @Field("TaskId") taskId: String,
                         @Field("tokenKey") tokenKey: String): Observable<BaseResp<SetoffCheckIn>>

    // 退乘报到
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=SetOffCheckIn")
    fun setOffCheckIn(@Field("TaskId") taskId: String,
                      @Field("tokenKey") tokenKey: String): Observable<BaseResp<String>>

    // 获取退乘酒测任务列表
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetSetOffAlcoholTestList")
    fun getSetoffAlcoholTestList(@Field("InstanceId") instanceId: String,
                                 @Field("tokenKey") tokenKey: String): Observable<BaseResp<List<SetoffAlcoholTest>>>

    // 获取退乘酒测任务实例
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetSetOffAlcoholTest")
    fun getSetOffAlcoholTest(@Field("InstanceId") instanceId: String,
                          @Field("TaskId") taskId: String,
                          @Field("tokenKey") tokenKey: String): Observable<BaseResp<List<SetoffAlcoholTest>>>

    // 退乘酒测确认
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=SetOffAlcoholTest")
    fun setoffAlcoholTest(@Field("TaskId") taskId: String,
                          @Field("tokenKey") tokenKey: String): Observable<BaseResp<String>>

    // 获取当前发车实例退乘任务实例列表
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetTasktance_SetOffList")
    fun getSetoffList(@Field("InstanceId") instanceId: String,
                      @Field("tokenKey") tokenKey: String): Observable<BaseResp<List<Setoff>>>

    // 退乘最终确认
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=SetTasktance_SetOffConfirm")
    fun setoffConfirm(@Field("TaskId") taskId: String,
                      @Field("tokenKey") tokenKey: String): Observable<BaseResp<String>>


}