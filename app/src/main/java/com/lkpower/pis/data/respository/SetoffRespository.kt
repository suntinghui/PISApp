package com.lkpower.pis.data.respository

import com.lkpower.pis.data.net.RetrofitFactory
import com.lkpower.pis.data.protocol.BaseResp
import com.lkpower.pis.data.api.SetoffApi
import com.lkpower.pis.data.api.SetoutApi
import com.lkpower.pis.data.protocol.*
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Inject

class SetoffRespository @Inject constructor() {

    // 获取退乘报到任务列表
    fun getOutCheckinList(instanceId: String, tokenKey: String): Observable<BaseResp<List<SetoutCheckIn>>> {
        return RetrofitFactory.instance.create(SetoutApi::class.java).getOutCheckInList(instanceId, tokenKey)
    }

    // 获取退乘报到任务列表
    fun getSetoffCheckInList(instanceId: String, tokenKey: String): Observable<BaseResp<List<SetoffCheckIn>>> {
        return RetrofitFactory.instance.create(SetoffApi::class.java).getSetoffCheckInList(instanceId, tokenKey)
    }

    // 获取退乘报到任务实例
    fun getSetoffCheckIn(instanceId: String, taskId: String, tokenKey: String): Observable<BaseResp<SetoffCheckIn>> {
        return RetrofitFactory.instance.create(SetoffApi::class.java).getSetoffCheckIn(instanceId, taskId, tokenKey)
    }

    // 退乘报到
    fun setOffCheckIn(taskId: String, tokenKey: String): Observable<BaseResp<Boolean>> {
        return RetrofitFactory.instance.create(SetoffApi::class.java).setOffCheckIn(taskId, tokenKey)
    }

    // 获取退乘酒测任务列表
    fun getSetoffAlcoholTestList(instanceId: String, tokenKey: String): Observable<BaseResp<List<SetoffAlcoholTest>>> {
        return RetrofitFactory.instance.create(SetoffApi::class.java).getSetoffAlcoholTestList(instanceId, tokenKey)
    }

    // 获取退乘酒测任务实例
    fun getSetOffAlcoholTest(instanceId: String, taskId: String, tokenKey: String): Observable<BaseResp<SetoffAlcoholTest>> {
        return RetrofitFactory.instance.create(SetoffApi::class.java).getSetOffAlcoholTest(instanceId, taskId, tokenKey)
    }

    // 退乘酒测确认
    fun setoffAlcoholTest(taskId: String, state: String, tokenKey: String): Observable<BaseResp<Boolean>> {
        return RetrofitFactory.instance.create(SetoffApi::class.java).setoffAlcoholTest(taskId, state, tokenKey)
    }

    // 获取当前发车实例退乘任务实例列表
    fun getSetoffList(instanceId: String, tokenKey: String): Observable<BaseResp<List<SetoffInfo>>> {
        return RetrofitFactory.instance.create(SetoffApi::class.java).getSetoffList(instanceId, tokenKey)
    }

    // 获取当前发车实例退乘任务实例
    fun getSetoff(instanceId: String, taskId: String, tokenKey: String): Observable<BaseResp<SetoffInfo>> {
        return RetrofitFactory.instance.create(SetoffApi::class.java).getSetoff(instanceId, taskId, tokenKey)
    }

    // 退乘最终确认
    fun setoffConfirm(taskId: String, TaskPlace: String, tokenKey: String): Observable<BaseResp<CommonReturn>> {
        return RetrofitFactory.instance.create(SetoffApi::class.java).setoffConfirm(taskId, TaskPlace, tokenKey)
    }


}