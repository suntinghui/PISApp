package com.lkpower.pis.service.impl

import com.lkpower.base.ext.convert
import com.lkpower.base.ext.convertBoolean
import com.lkpower.pis.data.protocol.*
import com.lkpower.pis.data.respository.SetoutRespository
import com.lkpower.pis.service.SetoutService
import io.reactivex.Observable
import javax.inject.Inject

class SetoutServiceImpl @Inject constructor() : SetoutService {

    @Inject
    lateinit var respository: SetoutRespository


    override fun getSetoutCheckInList(instanceId: String, tokenKey: String): Observable<List<SetoutCheckIn>> {
        return respository.getOutCheckinList(instanceId, tokenKey).convert()
    }

    override fun getSetoutCheckInDetail(instanceId: String, taskId: String, tokenKey: String): Observable<SetoutCheckIn> {
        return respository.getOutCheckinDetail(instanceId, taskId, tokenKey).convert()
    }

    override fun setOutCheckIn(taskId: String, tokenKey: String): Observable<Boolean> {
        return respository.setOutCheckin(taskId, tokenKey).convertBoolean()
    }

    override fun getSetoutAlcoholTestList(instanceId: String, tokenKey: String): Observable<List<SetoutAlcoholTest>> {
        return respository.getSetoutAlcoholTestList(instanceId, tokenKey).convert()
    }

    override fun getSetoutAlcoholTestDetail(instanceId: String, taskId: String, tokenKey: String): Observable<SetoutAlcoholTest> {
        return respository.getSetOutAlcoholTestDetail(instanceId, taskId, tokenKey).convert()
    }

    override fun setOutAlcoholTest(taskId: String, result: String, tokenKey: String): Observable<Boolean> {
        return respository.setOutAlcoholTest(taskId, result, tokenKey).convertBoolean()
    }

    override fun getTaskConveyList(instanceId: String, tokenKey: String): Observable<List<TaskConveyDetail>> {
        return respository.getTaskConveyList(instanceId, tokenKey).convert()
    }

    override fun getTaskConveyDetail(conveyDetailId: String, tokenKey: String): Observable<TaskConveyDetail> {
        return respository.getTaskConveyDetail(conveyDetailId, tokenKey).convert()
    }

    override fun taskRiskItemConfirm(itemId: String, feedBack: String, tokenKey: String): Observable<Boolean> {
        return respository.taskRiskItemConfirm(itemId, feedBack, tokenKey).convertBoolean()
    }

    override fun getSetoutGroupTaskList(instanceId: String, tokenKey: String): Observable<List<SetoutGroupTask>> {
        return respository.getSetoutGroupTaskList(instanceId, tokenKey).convert()
    }

    override fun getSetOutConfirmProjList(instanceId: String, groupTaskId: String, tokenKey: String): Observable<List<SetoutConfirmProj>> {
        return respository.getSetOutConfirmProjList(instanceId, groupTaskId, tokenKey).convert()
    }

    override fun setoutConfirmProj(taskId: String, tokenKey: String): Observable<Boolean> {
        return respository.setoutConfirmProj(taskId, tokenKey).convertBoolean()
    }

    override fun getSetoutList(instanceId: String, tokenKey: String): Observable<List<SetoutInfo>> {
        return respository.getSetoutList(instanceId, tokenKey).convert()
    }

    override fun getSetout(instanceId: String, taskId: String, tokenKey: String): Observable<SetoutInfo> {
        return respository.getSetout(instanceId, taskId, tokenKey).convert()
    }

    override fun setoutConfirm(taskId: String, tokenKey: String): Observable<Boolean> {
        return respository.setoutConfirm(taskId, tokenKey).convertBoolean()
    }


}