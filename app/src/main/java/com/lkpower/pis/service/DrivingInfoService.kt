package com.lkpower.pis.service

import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface DrivingInfoService {

    // 行车信息查询列表
    fun getDrivingInfoList(searchInfo: String, pageInfo: String, tokenKey: String): Observable<ListResult<DrivingInfo>>

    // 获取行车信息详细信息
    fun getDrivingInfoModel(id: String, tokenKey: String): Observable<DrivingInfo>

    // 行车信息上报
    fun setLearnDocRead(instanceId: String, remark: String, tokenKey: String): Observable<String>
}