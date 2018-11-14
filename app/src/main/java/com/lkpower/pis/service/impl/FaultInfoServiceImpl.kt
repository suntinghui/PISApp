package com.lkpower.pis.service.impl

import com.lkpower.pis.ext.convert
import com.lkpower.pis.data.protocol.*
import com.lkpower.pis.data.respository.DrivingInfoRespository
import com.lkpower.pis.data.respository.FaultInfoRespository
import com.lkpower.pis.data.respository.LearnDocRespository
import com.lkpower.pis.service.DrivingInfoService
import com.lkpower.pis.service.FaultInfoService
import io.reactivex.Observable
import javax.inject.Inject

class FaultInfoServiceImpl @Inject constructor() : FaultInfoService {

    @Inject
    lateinit var faultInfoRespository: FaultInfoRespository


    override fun getFaultInfoList(searchInfo: String, pageInfo: String, tokenKey: String): Observable<ListResult<FaultInfo>> {
        return faultInfoRespository.getFaultInfoList(searchInfo, pageInfo, tokenKey).convert()
    }

    override fun getFaultInfoModel(faultId: String, tokenKey: String): Observable<FaultInfo> {
        return faultInfoRespository.getFaultInfoModel(faultId, tokenKey).convert()
    }

    override fun addFaultInfo(tokenKey: String, faultInfo: String): Observable<CommonReturn> {
        return faultInfoRespository.addFaultInfo(tokenKey, faultInfo).convert()
    }

    override fun addFaultInfoConfirm(tokenKey: String, confirmInfo: String): Observable<CommonReturn> {
        return faultInfoRespository.addFaultInfoConfirm(tokenKey, confirmInfo).convert()
    }

    override fun getFailPartList(keyword: String): Observable<List<SysDic>> {
        return faultInfoRespository.getFailPartList(keyword).convert()
    }

    override fun getFaultTypeList(relParentId: String, keyword: String): Observable<List<SysDic>> {
        return faultInfoRespository.getFaultTypeList(relParentId, keyword).convert()
    }

    override fun getCheckTypeList(): Observable<List<SysDic>> {
        return faultInfoRespository.getCheckTypeList().convert()
    }



}