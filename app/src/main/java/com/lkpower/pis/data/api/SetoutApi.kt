package com.lkpower.pis.data.api

import com.lkpower.pis.data.protocol.BaseResp
import com.lkpower.pis.data.protocol.*
import io.reactivex.Observable
import retrofit2.http.*

/*
出乘相关
 */
interface SetoutApi {

    // 获取出乘报到任务列表
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetSetOutCheckInList")
    fun getOutCheckInList(@Field("InstanceId") instanceId: String,
                          @Field("tokenKey") tokenKey: String): Observable<BaseResp<List<SetoutCheckIn>>>

    // 获取出乘报到任务实例（详情）
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetSetOutCheckIn")
    fun getOutCheckInDetail(@Field("InstanceId") instanceId: String,
                            @Field("TaskId") taskId: String,
                            @Field("tokenKey") tokenKey: String): Observable<BaseResp<SetoutCheckIn>>

    // 出乘报到
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=SetOutCheckIn")
    fun setOutCheckin(@Field("TaskId") taskId: String,
                      @Field("tokenKey") tokenKey: String): Observable<BaseResp<Boolean>>

    // 获取出乘酒测任务列表
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetSetOutAlcoholTestList")
    fun getSetoutAlcoholTestList(@Field("InstanceId") instanceId: String,
                                 @Field("tokenKey") taskId: String
    ): Observable<BaseResp<List<SetoutAlcoholTest>>>

    // 获取出乘酒测任务实例
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetSetOutAlcoholTest")
    fun getSetOutAlcoholTestDetail(@Field("InstanceId") instanceId: String,
                                   @Field("TaskId") taskId: String,
                                   @Field("tokenKey") tokenKey: String
    ): Observable<BaseResp<SetoutAlcoholTest>>

    // 酒测确认
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=SetOutAlcoholTest")
    fun setOutAlcoholTest(@Field("TaskId") taskId: String,
                          @Field("Result") result: String,
                          @Field("tokenKey") tokenKey: String): Observable<BaseResp<Boolean>>

    // 获取任务计划列表
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetTaskConveyList")
    fun getTaskConveyList(@Field("InstanceId") instanceId: String,
                          @Field("tokenKey") tokenKey: String): Observable<BaseResp<List<TaskConveyDetail>>>

    // 获取任务计划实例（详情）
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetTaskConveyModel")
    fun getTaskConveyDetail(@Field("ConveyDetailId") conveyDetailId: String,
                            @Field("tokenKey") tokenKey: String): Observable<BaseResp<TaskConveyDetail>>

    // 计划风险项目确认
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=TaskRiskItemConfirm")
    fun taskRiskItemConfirm(@Field("ItemId") itemId: String,
                            @Field("FeedBack") feedBack: String,
                            @Field("tokenKey") tokenKey: String): Observable<BaseResp<Boolean>>

    // 获取出乘确认项目主任务列表
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetSetOutGroupTaskList")
    fun getSetoutGroupTaskList(@Field("InstanceId") instanceId: String,
                               @Field("tokenKey") tokenKey: String): Observable<BaseResp<List<SetoutGroupTask>>>

    // 获取出乘项目确认任务具体项目列表
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetSetOutConfirmProjList")
    fun getSetOutConfirmProjList(@Field("InstanceId") instanceId: String,
                                 @Field("GroupTaskId") groupTaskId: String,
                                 @Field("tokenKey") tokenKey: String): Observable<BaseResp<List<SetoutConfirmProj>>>

    // 出乘项目确认
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=SetOutConfirmProj")
    fun setoutConfirmProj(@Field("TaskId") taskId: String,
                          @Field("TaskStatus") taskStatus: String,
                          @Field("ConfirmRemark") ConfirmRemark: String,
                          @Field("tokenKey") tokenKey: String): Observable<BaseResp<Boolean>>


    // 出乘任务实例列表
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetTasktance_SetOutList")
    fun getSetoutList(@Field("InstanceId") instanceId: String,
                      @Field("tokenKey") tokenKey: String): Observable<BaseResp<List<SetoutInfo>>>

    // 出乘任务实例详情
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=GetSetOut")
    fun getSetout(@Field("InstanceId") instanceId: String,
                  @Field("TaskId") taskId: String,
                  @Field("tokenKey") tokenKey: String): Observable<BaseResp<SetoutInfo>>

    // 出乘最终确认
    @FormUrlEncoded
    @POST("WorkTask.ashx?Commond=SetTasktance_SetOutConfirm")
    fun setoutConfirm(@Field("TaskId") taskId: String,
                      @Field("TaskPlace") TaskPlace: String,
                      @Field("tokenKey") tokenKey: String): Observable<BaseResp<CommonReturn>>


}