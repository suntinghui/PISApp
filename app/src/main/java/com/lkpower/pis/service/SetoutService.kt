package com.lkpower.pis.service

import com.lkpower.pis.data.protocol.*
import io.reactivex.Observable

interface SetoutService {

    /*
     获取出乘报到任务列表
      */
    fun getSetoutCheckInList(instanceId: String, tokenKey: String): Observable<List<SetoutCheckIn>>

    /**
     * 获取出乘报到任务实例
     */
    fun getSetoutCheckInDetail(instanceId: String, taskId: String, tokenKey: String): Observable<SetoutCheckIn>

    /*
      出乘报到
     */
    fun setOutCheckIn(taskId: String, tokenKey: String): Observable<Boolean>

    /*
     获取出乘酒测任务列表
      */
    fun getSetoutAlcoholTestList(instanceId: String, tokenKey: String): Observable<List<SetoutAlcoholTest>>

    /**
     * 获取出乘酒测任务实例
     */
    fun getSetoutAlcoholTestDetail(instanceId: String, taskId: String, tokenKey: String): Observable<SetoutAlcoholTest>

    /*
      酒测
     */
    fun setOutAlcoholTest(taskId: String, result: String, tokenKey: String): Observable<Boolean>

    /*
    获取任务计划列表
     */
    fun getTaskConveyList(instanceId: String, tokenKey: String): Observable<List<TaskConveyDetail>>

    /*
    获取任务计划实例（详情）
     */
    fun getTaskConveyDetail(conveyDetailId: String, tokenKey: String): Observable<TaskConveyDetail>

    /*
    计划风险项目确认
     */
    fun taskRiskItemConfirm(itemId: String, feedBack: String, tokenKey: String): Observable<Boolean>

    // 剩余未确认风险项数量
    fun getNoDoneRiskCount(ConveyDetailId: String, tokenKey: String): Observable<String>

    // 计划任务确认
    fun taskConveyConfirm(ConveyDetailId: String, feedBack: String, tokenKey: String): Observable<Boolean>

    /*
   获取出乘确认项目主任务列表
    */
    fun getSetoutGroupTaskList(instanceId: String, tokenKey: String): Observable<List<SetoutGroupTask>>

    /*
    获取出乘项目确认任务具体项目列表
     */
    fun getSetOutConfirmProjList(instanceId: String, groupTaskId: String, tokenKey: String): Observable<List<SetoutConfirmProj>>

    /*
    出乘项目确认
     */
    fun setoutConfirmProj(taskId: String, confirmRemark: String, tokenKey: String): Observable<Boolean>

    /*
    获取当前发车实例出乘任务实例列表
     */
    fun getSetoutList(instanceId: String, tokenKey: String): Observable<List<SetoutInfo>>

    /*
    获取出乘任务实例
     */
    fun getSetout(instanceId: String, taskId: String, tokenKey: String): Observable<SetoutInfo>

    /*
    出乘最终确认
     */
    fun setoutConfirm(taskId: String, TaskPlace: String, tokenKey: String): Observable<CommonReturn>


}