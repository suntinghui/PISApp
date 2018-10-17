package com.lkpower.pis.service.impl

import com.lkpower.base.ext.convert
import com.lkpower.pis.data.protocol.OutCheckInInfo
import com.lkpower.pis.data.protocol.UserInfo
import com.lkpower.pis.data.protocol.XJ_LCFCInfo
import com.lkpower.pis.data.respository.SettingRespository
import com.lkpower.pis.data.respository.TasktanceRespository
import com.lkpower.pis.data.respository.UserRespository
import com.lkpower.pis.service.TasktanceService
import com.lkpower.pis.service.UserService
import io.reactivex.Observable
import javax.inject.Inject

class TasktanceServiceImpl @Inject constructor() : TasktanceService {

    @Inject
    lateinit var respository: TasktanceRespository


    override fun getOutCheckInList(instanceId: String, tokenKey: String): Observable<List<OutCheckInInfo>> {
        return respository.getOutCheckinList(instanceId, tokenKey).convert()
    }

    override fun getOutCheckInDetail(instanceId: String, taskId: String, tokenKey: String): Observable<OutCheckInInfo> {
        return respository.getOutCheckinDetail(instanceId, taskId, tokenKey).convert()
    }

    override fun setOutCheckIn(taskId: String, tokenKey: String): Observable<String> {
        return respository.setOutCheckin(taskId, tokenKey).convert()
    }


}