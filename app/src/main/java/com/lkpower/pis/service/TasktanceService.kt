package com.lkpower.pis.service

import com.lkpower.pis.data.protocol.SetoutAlcoholTestInfo
import com.lkpower.pis.data.protocol.SetoutCheckInInfo
import com.lkpower.pis.data.protocol.SetoutInfo
import io.reactivex.Observable

interface TasktanceService {

    /*
     获取出乘报到任务列表
      */
    fun getSetoutCheckInList(instanceId: String, tokenKey: String): Observable<List<SetoutCheckInInfo>>

    /**
     * 获取出乘报到任务实例
     */
    fun getSetoutCheckInDetail(instanceId: String, taskId: String, tokenKey: String): Observable<SetoutCheckInInfo>

    /*
      出乘报到
     */
    fun setOutCheckIn(taskId: String, tokenKey: String): Observable<String>

    /*
     获取出乘酒测任务列表
      */
    fun getSetoutAlcoholTestList(instanceId: String, tokenKey: String): Observable<List<SetoutAlcoholTestInfo>>

    /**
     * 获取出乘酒测任务实例
     */
    fun getSetoutAlcoholTestDetail(instanceId: String, taskId: String, tokenKey: String): Observable<SetoutAlcoholTestInfo>

    /*
      酒测
     */
    fun setOutAlcoholTest(taskId: String, result: String, tokenKey: String): Observable<String>

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
    fun setoutConfirm(taskId: String, tokenKey: String): Observable<String>


}