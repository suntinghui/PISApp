package com.lkpower.pis.service.impl

import com.lkpower.base.ext.convert
import com.lkpower.base.ext.convertBoolean
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.EmergencyInfo
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.respository.DrivingInfoRespository
import com.lkpower.pis.data.respository.EmergencyInfoRespository
import com.lkpower.pis.data.respository.LearnDocRespository
import com.lkpower.pis.service.DrivingInfoService
import com.lkpower.pis.service.EmergencyInfoService
import io.reactivex.Observable
import javax.inject.Inject

class EmergencyInfoServiceImpl @Inject constructor() : EmergencyInfoService {

    @Inject
    lateinit var emergencyInfoRespository: EmergencyInfoRespository

    override fun getEmergencyInfoList(searchInfo: String, pageInfo: String, tokenKey: String): Observable<ListResult<EmergencyInfo>> {
        return emergencyInfoRespository.getEmergencyInfoList(searchInfo, pageInfo, tokenKey).convert()
    }

    override fun getEmergencyInfoModel(emInfoId: String, tokenKey: String): Observable<EmergencyInfo> {
        return emergencyInfoRespository.getEmergencyInfoModel(emInfoId, tokenKey).convert()
    }

    override fun addEmergencyInfo(info: String, tokenKey: String): Observable<CommonReturn> {
        return emergencyInfoRespository.addEmergencyInfo(info, tokenKey).convert()
    }


}