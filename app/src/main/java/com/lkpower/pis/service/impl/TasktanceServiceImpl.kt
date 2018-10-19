package com.lkpower.pis.service.impl

import com.lkpower.base.ext.convert
import com.lkpower.pis.data.protocol.SetoutAlcoholTestInfo
import com.lkpower.pis.data.protocol.SetoutCheckInInfo
import com.lkpower.pis.data.protocol.SetoutInfo
import com.lkpower.pis.data.respository.TasktanceRespository
import com.lkpower.pis.service.TasktanceService
import io.reactivex.Observable
import javax.inject.Inject

class TasktanceServiceImpl @Inject constructor() : TasktanceService {

    @Inject
    lateinit var respository: TasktanceRespository


    override fun getSetoutCheckInList(instanceId: String, tokenKey: String): Observable<List<SetoutCheckInInfo>> {
        return respository.getOutCheckinList(instanceId, tokenKey).convert()
    }

    override fun getSetoutCheckInDetail(instanceId: String, taskId: String, tokenKey: String): Observable<SetoutCheckInInfo> {
        return respository.getOutCheckinDetail(instanceId, taskId, tokenKey).convert()
    }

    override fun setOutCheckIn(taskId: String, tokenKey: String): Observable<String> {
        return respository.setOutCheckin(taskId, tokenKey).convert()
    }

    override fun getSetoutAlcoholTestList(instanceId: String, tokenKey: String): Observable<List<SetoutAlcoholTestInfo>> {
        return respository.getSetoutAlcoholTestList(instanceId, tokenKey).convert()
    }

    override fun getSetoutAlcoholTestDetail(instanceId: String, taskId: String, tokenKey: String): Observable<SetoutAlcoholTestInfo> {
        return respository.getSetOutAlcoholTestDetail(instanceId, taskId, tokenKey).convert()
    }

    override fun setOutAlcoholTest(taskId: String, result: String, tokenKey: String): Observable<String> {
        return respository.setOutAlcoholTest(taskId, result, tokenKey).convert()
    }

    override fun getSetoutList(instanceId: String, tokenKey: String): Observable<List<SetoutInfo>> {
        return respository.getSetoutList(instanceId, tokenKey).convert()
    }

    override fun getSetout(instanceId: String, taskId: String, tokenKey: String): Observable<SetoutInfo> {
        return respository.getSetout(instanceId, taskId, tokenKey).convert()
    }

    override fun setoutConfirm(taskId: String, tokenKey: String): Observable<String> {
        return respository.setoutConfirm(taskId, tokenKey).convert()
    }


}