package com.lkpower.pis.service.impl

import com.lkpower.base.ext.convert
import com.lkpower.pis.data.protocol.Setoff
import com.lkpower.pis.data.protocol.SetoffAlcoholTest
import com.lkpower.pis.data.protocol.SetoffCheckIn
import com.lkpower.pis.data.protocol.SetoutCheckIn
import com.lkpower.pis.data.respository.SetoffRespository
import com.lkpower.pis.service.SetoffService
import io.reactivex.Observable
import javax.inject.Inject

class SetoffServiceImpl @Inject constructor() : SetoffService {

    @Inject
    lateinit var respository: SetoffRespository

    override fun getOutCheckinList(instanceId: String, tokenKey: String): Observable<List<SetoutCheckIn>> {
        return respository.getOutCheckinList(instanceId, tokenKey).convert()
    }

    override fun getSetoffCheckInList(instanceId: String, tokenKey: String): Observable<List<SetoffCheckIn>> {
        return respository.getSetoffCheckInList(instanceId, tokenKey).convert()
    }

    override fun getSetoffCheckIn(instanceId: String, taskId: String, tokenKey: String): Observable<SetoffCheckIn> {
        return respository.getSetoffCheckIn(instanceId, taskId, tokenKey).convert()
    }

    override fun setOffCheckIn(taskId: String, tokenKey: String): Observable<String> {
        return respository.setOffCheckIn(taskId, tokenKey).convert()
    }

    override fun getSetoffAlcoholTestList(instanceId: String, tokenKey: String): Observable<List<SetoffAlcoholTest>> {
        return respository.getSetoffAlcoholTestList(instanceId, tokenKey).convert()
    }

    override fun getSetOffAlcoholTest(instanceId: String, taskId: String, tokenKey: String): Observable<List<SetoffAlcoholTest>> {
        return respository.getSetOffAlcoholTest(instanceId, taskId, tokenKey).convert()
    }

    override fun setoffAlcoholTest(taskId: String, tokenKey: String): Observable<String> {
        return respository.setoffAlcoholTest(taskId, tokenKey).convert()
    }

    override fun getSetoffList(instanceId: String, tokenKey: String): Observable<List<Setoff>> {
        return respository.getSetoffList(instanceId, tokenKey).convert()
    }

    override fun setoffConfirm(taskId: String, tokenKey: String): Observable<String> {
        return respository.setoffConfirm(taskId, tokenKey).convert()
    }

}