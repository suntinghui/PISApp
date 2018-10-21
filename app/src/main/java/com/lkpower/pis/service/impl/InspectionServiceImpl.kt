package com.lkpower.pis.service.impl

import com.lkpower.base.ext.convert
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.protocol.MissionStateInfo
import com.lkpower.pis.data.protocol.XJ_CZSL
import com.lkpower.pis.data.respository.DrivingInfoRespository
import com.lkpower.pis.data.respository.InspectionRespository
import com.lkpower.pis.data.respository.LearnDocRespository
import com.lkpower.pis.service.DrivingInfoService
import com.lkpower.pis.service.InspectionService
import io.reactivex.Observable
import javax.inject.Inject

class InspectionServiceImpl @Inject constructor() : InspectionService {

    @Inject
    lateinit var inspectionRespository: InspectionRespository


    override fun getSiteList(instanceId: String, tokenKey: String): Observable<List<XJ_CZSL>> {
        return inspectionRespository.getSiteList(instanceId, tokenKey).convert()
    }

    override fun getXJTaskList(instanceId: String, siteId: String, tokenKey: String): Observable<MissionStateInfo> {
        return inspectionRespository.getXJTaskList(instanceId, siteId, tokenKey).convert()
    }

    override fun getXJTaskModel(taskId: String, tokenKey: String): Observable<MissionStateInfo> {
        return inspectionRespository.getXJTaskModel(taskId, tokenKey).convert()
    }

    override fun updateMissionInfoExt(taskId: String, tokenKey: String): Observable<String> {
        return inspectionRespository.updateMissionInfoExt(taskId, tokenKey).convert()
    }

    override fun alarmLogInfo(instanceId: String, deviceId: String, missionInstanceId: String, remark: String, tokenKey: String): Observable<String> {
        return inspectionRespository.alarmLogInfo(instanceId, deviceId, missionInstanceId, remark, tokenKey).convert()
    }

    override fun alarmUpdateLogInfo(instanceId: String, deviceId: String, missionInstanceId: String, remark: String, tokenKey: String): Observable<String> {
        return inspectionRespository.alarmUpdateLogInfo(instanceId, deviceId, missionInstanceId, remark, tokenKey).convert()
    }


}