package com.lkpower.pis.service

import com.lkpower.base.data.net.RetrofitFactory
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.api.FaultInfoApi
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.FaultInfo
import com.lkpower.pis.data.protocol.ListResult
import io.reactivex.Observable

interface FaultInfoService {

    // 故障信息查询列表
    fun getFaultInfoList(searchInfo: String, pageInfo: String, tokenKey: String): Observable<ListResult<FaultInfo>>

    // 获取故障信息详细信息
    fun getFaultInfoModel(faultId: String, tokenKey: String): Observable<FaultInfo>

    // 故障反馈
    fun addFaultInfo(tokenKey: String, faultInfo: String): Observable<String>

    // 故障修复确认
    fun addFaultInfoConfirm(tokenKey: String, confirmInfo: String): Observable<String>
}