package com.lkpower.pis.data.respository

import com.lkpower.base.data.net.RetrofitFactory
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.api.TasktanceApi
import com.lkpower.pis.data.protocol.SetoutAlcoholTestInfo
import com.lkpower.pis.data.protocol.SetoutCheckInInfo
import com.lkpower.pis.data.protocol.SetoutInfo
import com.lkpower.pis.data.protocol.TaskConveyDetail
import io.reactivex.Observable
import javax.inject.Inject

class TasktanceRespository @Inject constructor() {

    fun getOutCheckinList(instanceId: String, tokenKey: String): Observable<BaseResp<List<SetoutCheckInInfo>>> {
        return RetrofitFactory.instance.create(TasktanceApi::class.java).getOutCheckInList(instanceId, tokenKey)
    }

    fun getOutCheckinDetail(instanceId: String, taskId: String, tokenKey: String): Observable<BaseResp<SetoutCheckInInfo>> {
        return RetrofitFactory.instance.create(TasktanceApi::class.java).getOutCheckInDetail(instanceId, taskId, tokenKey)
    }

    fun setOutCheckin(taskId: String, tokenKey: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(TasktanceApi::class.java).setOutCheckin(taskId, tokenKey)
    }

    fun getSetoutAlcoholTestList(instanceId: String, tokenKey: String): Observable<BaseResp<List<SetoutAlcoholTestInfo>>> {
        return RetrofitFactory.instance.create(TasktanceApi::class.java).getSetoutAlcoholTestList(instanceId, tokenKey)
    }

    fun getSetOutAlcoholTestDetail(instanceId: String, taskId: String, tokenKey: String): Observable<BaseResp<SetoutAlcoholTestInfo>> {
        return RetrofitFactory.instance.create(TasktanceApi::class.java).getSetOutAlcoholTestDetail(instanceId, taskId, tokenKey)
    }

    fun setOutAlcoholTest(taskId: String, result: String, tokenKey: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(TasktanceApi::class.java).setOutAlcoholTest(taskId, result, tokenKey)
    }

    fun getTaskConveyList(instanceId: String, tokenKey: String): Observable<BaseResp<List<TaskConveyDetail>>> {
        return RetrofitFactory.instance.create(TasktanceApi::class.java).getTaskConveyList(instanceId, tokenKey)
    }

    fun getTaskConveyDetail(conveyDetailId: String, tokenKey: String): Observable<BaseResp<TaskConveyDetail>> {
        return RetrofitFactory.instance.create(TasktanceApi::class.java).getTaskConveyDetail(conveyDetailId, tokenKey)
    }

    fun taskRiskItemConfirm(itemId: String, feedBack: String, tokenKey: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(TasktanceApi::class.java).taskRiskItemConfirm(itemId, feedBack, tokenKey)
    }

    fun getSetoutList(instanceId: String, tokenKey: String): Observable<BaseResp<List<SetoutInfo>>> {
        return RetrofitFactory.instance.create(TasktanceApi::class.java).getSetoutList(instanceId, tokenKey)
    }

    fun getSetout(instanceId: String, taskId: String, tokenKey: String): Observable<BaseResp<SetoutInfo>> {
        return RetrofitFactory.instance.create(TasktanceApi::class.java).getSetout(instanceId, taskId, tokenKey)
    }

    fun setoutConfirm(taskId: String, tokenKey: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(TasktanceApi::class.java).setoutConfirm(taskId, tokenKey)
    }


}