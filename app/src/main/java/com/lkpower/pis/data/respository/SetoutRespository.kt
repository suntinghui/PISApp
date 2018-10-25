package com.lkpower.pis.data.respository

import com.lkpower.base.data.net.RetrofitFactory
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.api.SetoutApi
import com.lkpower.pis.data.protocol.*
import io.reactivex.Observable
import javax.inject.Inject

class SetoutRespository @Inject constructor() {

    fun getOutCheckinList(instanceId: String, tokenKey: String): Observable<BaseResp<List<SetoutCheckIn>>> {
        return RetrofitFactory.instance.create(SetoutApi::class.java).getOutCheckInList(instanceId, tokenKey)
    }

    fun getOutCheckinDetail(instanceId: String, taskId: String, tokenKey: String): Observable<BaseResp<SetoutCheckIn>> {
        return RetrofitFactory.instance.create(SetoutApi::class.java).getOutCheckInDetail(instanceId, taskId, tokenKey)
    }

    fun setOutCheckin(taskId: String, tokenKey: String): Observable<BaseResp<Boolean>> {
        return RetrofitFactory.instance.create(SetoutApi::class.java).setOutCheckin(taskId, tokenKey)
    }

    fun getSetoutAlcoholTestList(instanceId: String, tokenKey: String): Observable<BaseResp<List<SetoutAlcoholTest>>> {
        return RetrofitFactory.instance.create(SetoutApi::class.java).getSetoutAlcoholTestList(instanceId, tokenKey)
    }

    fun getSetOutAlcoholTestDetail(instanceId: String, taskId: String, tokenKey: String): Observable<BaseResp<SetoutAlcoholTest>> {
        return RetrofitFactory.instance.create(SetoutApi::class.java).getSetOutAlcoholTestDetail(instanceId, taskId, tokenKey)
    }

    fun setOutAlcoholTest(taskId: String, result: String, tokenKey: String): Observable<BaseResp<Boolean>> {
        return RetrofitFactory.instance.create(SetoutApi::class.java).setOutAlcoholTest(taskId, result, tokenKey)
    }

    fun getTaskConveyList(instanceId: String, tokenKey: String): Observable<BaseResp<List<TaskConveyDetail>>> {
        return RetrofitFactory.instance.create(SetoutApi::class.java).getTaskConveyList(instanceId, tokenKey)
    }

    fun getTaskConveyDetail(conveyDetailId: String, tokenKey: String): Observable<BaseResp<TaskConveyDetail>> {
        return RetrofitFactory.instance.create(SetoutApi::class.java).getTaskConveyDetail(conveyDetailId, tokenKey)
    }

    fun taskRiskItemConfirm(itemId: String, feedBack: String, tokenKey: String): Observable<BaseResp<Boolean>> {
        return RetrofitFactory.instance.create(SetoutApi::class.java).taskRiskItemConfirm(itemId, feedBack, tokenKey)
    }

    fun getSetoutGroupTaskList(instanceId: String, tokenKey: String): Observable<BaseResp<List<SetoutGroupTask>>> {
        return RetrofitFactory.instance.create(SetoutApi::class.java).getSetoutGroupTaskList(instanceId, tokenKey)
    }

    fun getSetOutConfirmProjList(instanceId: String, groupTaskId: String, tokenKey: String): Observable<BaseResp<List<SetoutConfirmProj>>> {
        return RetrofitFactory.instance.create(SetoutApi::class.java).getSetOutConfirmProjList(instanceId, groupTaskId, tokenKey)
    }

    fun setoutConfirmProj(taskId: String, tokenKey: String): Observable<BaseResp<Boolean>> {
        return RetrofitFactory.instance.create(SetoutApi::class.java).setoutConfirmProj(taskId, tokenKey)
    }

    fun getSetoutList(instanceId: String, tokenKey: String): Observable<BaseResp<List<SetoutInfo>>> {
        return RetrofitFactory.instance.create(SetoutApi::class.java).getSetoutList(instanceId, tokenKey)
    }

    fun getSetout(instanceId: String, taskId: String, tokenKey: String): Observable<BaseResp<SetoutInfo>> {
        return RetrofitFactory.instance.create(SetoutApi::class.java).getSetout(instanceId, taskId, tokenKey)
    }

    fun setoutConfirm(taskId: String, tokenKey: String): Observable<BaseResp<Boolean>> {
        return RetrofitFactory.instance.create(SetoutApi::class.java).setoutConfirm(taskId, tokenKey)
    }


}