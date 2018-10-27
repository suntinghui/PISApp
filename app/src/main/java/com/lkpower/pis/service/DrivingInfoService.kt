package com.lkpower.pis.service

import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.ListResult
import io.reactivex.Observable

interface DrivingInfoService {

    // 行车信息查询列表
    fun getDrivingInfoList(searchInfo: String, pageInfo: String, tokenKey: String): Observable<ListResult<DrivingInfo>>

    // 获取行车信息详细信息
    fun getDrivingInfoModel(id: String, tokenKey: String): Observable<DrivingInfo>

    // 行车信息上报
    fun updateDrivingInfo(instanceId: String, remark: String, uuid: String, tokenKey: String): Observable<CommonReturn>
}