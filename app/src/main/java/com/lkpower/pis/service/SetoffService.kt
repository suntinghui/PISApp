package com.lkpower.pis.service

import com.lkpower.base.data.net.RetrofitFactory
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.api.SetoffApi
import com.lkpower.pis.data.api.SetoutApi
import com.lkpower.pis.data.protocol.*
import io.reactivex.Observable

interface SetoffService {

    // 获取退乘报到任务列表
    fun getOutCheckinList(instanceId: String, tokenKey: String): Observable<List<SetoutCheckIn>>

    // 获取退乘报到任务列表
    fun getSetoffCheckInList(instanceId: String, tokenKey: String): Observable<List<SetoffCheckIn>>

    // 获取退乘报到任务实例
    fun getSetoffCheckIn(instanceId: String, taskId: String, tokenKey: String): Observable<SetoffCheckIn>

    // 退乘报到
    fun setOffCheckIn(taskId: String, tokenKey: String): Observable<String>

    // 获取退乘酒测任务列表
    fun getSetoffAlcoholTestList(instanceId: String, tokenKey: String): Observable<List<SetoffAlcoholTest>>

    // 获取退乘酒测任务实例
    fun getSetOffAlcoholTest(instanceId: String, taskId: String, tokenKey: String): Observable<List<SetoffAlcoholTest>>

    // 退乘酒测确认
    fun setoffAlcoholTest(taskId: String, tokenKey: String): Observable<String>

    // 获取当前发车实例退乘任务实例列表
    fun getSetoffList(instanceId: String, tokenKey: String): Observable<List<Setoff>>

    // 退乘最终确认
    fun setoffConfirm(taskId: String, tokenKey: String): Observable<String>


}