package com.lkpower.pis.service

import com.lkpower.base.data.net.RetrofitFactory
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.api.EmergencyInfoApi
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.EmergencyInfo
import com.lkpower.pis.data.protocol.ListResult
import io.reactivex.Observable

interface EmergencyInfoService {

    // 应急信息查询列表
    fun getEmergencyInfoList(searchInfo: String, pageInfo: String, tokenKey: String): Observable<ListResult<EmergencyInfo>>

    // 应急处置信息明细
    fun getEmergencyInfoModel(emInfoId: String, tokenKey: String): Observable<EmergencyInfo>

    // 应急处置反馈
    fun addEmergencyInfo(instanceId: String, remark: String, tokenKey: String): Observable<String>
}