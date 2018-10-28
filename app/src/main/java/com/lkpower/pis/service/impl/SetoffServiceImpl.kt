package com.lkpower.pis.service.impl

import com.lkpower.base.ext.convert
import com.lkpower.base.ext.convertBoolean
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.SetoffAlcoholTest
import com.lkpower.pis.data.protocol.SetoffCheckIn
import com.lkpower.pis.data.protocol.SetoffInfo
import com.lkpower.pis.data.respository.SetoffRespository
import com.lkpower.pis.service.SetoffService
import io.reactivex.Observable
import javax.inject.Inject

class SetoffServiceImpl @Inject constructor() : SetoffService {

    @Inject
    lateinit var respository: SetoffRespository

    override fun getSetoffCheckInList(instanceId: String, tokenKey: String): Observable<List<SetoffCheckIn>> {
        return respository.getSetoffCheckInList(instanceId, tokenKey).convert()
    }

    override fun getSetoffCheckIn(instanceId: String, taskId: String, tokenKey: String): Observable<SetoffCheckIn> {
        return respository.getSetoffCheckIn(instanceId, taskId, tokenKey).convert()
    }

    override fun setOffCheckIn(taskId: String, tokenKey: String): Observable<Boolean> {
        return respository.setOffCheckIn(taskId, tokenKey).convertBoolean()
    }

    override fun getSetoffAlcoholTestList(instanceId: String, tokenKey: String): Observable<List<SetoffAlcoholTest>> {
        return respository.getSetoffAlcoholTestList(instanceId, tokenKey).convert()
    }

    override fun getSetOffAlcoholTest(instanceId: String, taskId: String, tokenKey: String): Observable<SetoffAlcoholTest> {
        return respository.getSetOffAlcoholTest(instanceId, taskId, tokenKey).convert()
    }

    override fun setoffAlcoholTest(taskId: String, state: String, tokenKey: String): Observable<Boolean> {
        return respository.setoffAlcoholTest(taskId, state, tokenKey).convertBoolean()
    }

    override fun getSetoffList(instanceId: String, tokenKey: String): Observable<List<SetoffInfo>> {
        return respository.getSetoffList(instanceId, tokenKey).convert()
    }

    override fun getSetoff(instanceId: String, taskId: String, tokenKey: String): Observable<SetoffInfo> {
        return respository.getSetoff(instanceId, taskId, tokenKey).convert()
    }

    override fun setoffConfirm(taskId: String, tokenKey: String): Observable<CommonReturn> {
        return respository.setoffConfirm(taskId, tokenKey).convert()
    }

}