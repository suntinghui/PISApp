package com.lkpower.pis.data.respository

import com.lkpower.base.data.net.RetrofitFactory
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.api.TasktanceApi
import com.lkpower.pis.data.api.UserApi
import com.lkpower.pis.data.protocol.OutCheckInInfo
import com.lkpower.pis.data.protocol.UserInfo
import com.lkpower.pis.data.protocol.XJ_LCFCInfo
import io.reactivex.Observable
import javax.inject.Inject

class TasktanceRespository @Inject constructor() {

    fun getOutCheckinList(instanceId: String, tokenKey: String): Observable<BaseResp<List<OutCheckInInfo>>> {
        return RetrofitFactory.instance.create(TasktanceApi::class.java).getOutCheckInList(instanceId, tokenKey)
    }

    fun getOutCheckinDetail(instanceId: String, taskId: String, tokenKey: String): Observable<BaseResp<OutCheckInInfo>> {
        return RetrofitFactory.instance.create(TasktanceApi::class.java).getOutCheckInDetail(instanceId, taskId, tokenKey)
    }

    fun setOutCheckin(taskId: String, tokenKey: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(TasktanceApi::class.java).setOutCheckin(taskId, tokenKey)
    }

}