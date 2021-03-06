package com.lkpower.pis.service.impl

import com.lkpower.pis.ext.convert
import com.lkpower.pis.ext.convertBoolean
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.respository.DrivingInfoRespository
import com.lkpower.pis.data.respository.LearnDocRespository
import com.lkpower.pis.service.DrivingInfoService
import io.reactivex.Observable
import javax.inject.Inject

class DrivingInfoServiceImpl @Inject constructor() : DrivingInfoService {

    @Inject
    lateinit var drivingRespository: DrivingInfoRespository


    override fun getDrivingInfoList(searchInfo: String, pageInfo: String, tokenKey: String): Observable<ListResult<DrivingInfo>> {
        return drivingRespository.getDrivingInfoList(searchInfo, pageInfo, tokenKey).convert()
    }

    override fun getDrivingInfoModel(id: String, tokenKey: String): Observable<DrivingInfo> {
        return drivingRespository.getDrivingInfoModel(id, tokenKey).convert()
    }

    override fun updateDrivingInfo(instanceId: String, remark: String, uuid: String, tokenKey: String): Observable<CommonReturn> {
        return drivingRespository.updateDrivingInfo(instanceId, remark, uuid, tokenKey).convert()
    }
}