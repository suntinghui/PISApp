package com.lkpower.pis.service.impl

import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.base.ext.convert
import com.lkpower.pis.data.protocol.*
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

    override fun getTaskConveyList(instanceId: String, tokenKey: String): Observable<List<TaskConveyDetail>> {
        return respository.getTaskConveyList(instanceId, tokenKey).convert()
    }

    override fun getTaskConveyDetail(conveyDetailId: String, tokenKey: String): Observable<TaskConveyDetail> {
        return respository.getTaskConveyDetail(conveyDetailId, tokenKey).convert()
    }

    override fun taskRiskItemConfirm(itemId: String, feedBack: String, tokenKey: String): Observable<String> {
        return respository.taskRiskItemConfirm(itemId, feedBack, tokenKey).convert()
    }

    override fun getSetoutGroupTaskList(instanceId: String, tokenKey: String): Observable<List<SetoutGroupTask>> {
        return respository.getSetoutGroupTaskList(instanceId, tokenKey).convert()
    }

    override fun getSetOutConfirmProjList(instanceId: String, groupTaskId: String, tokenKey: String): Observable<List<SetoutConfirmProj>> {
        return respository.getSetOutConfirmProjList(instanceId, groupTaskId, tokenKey).convert()
    }

    override fun setoutConfirmProj(taskId: String, tokenKey: String): Observable<String> {
        return respository.setoutConfirmProj(taskId, tokenKey).convert()
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