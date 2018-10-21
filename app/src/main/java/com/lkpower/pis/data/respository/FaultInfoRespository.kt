package com.lkpower.pis.data.respository

import com.lkpower.base.data.net.RetrofitFactory
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.api.DrivingInfoApi
import com.lkpower.pis.data.api.FaultInfoApi
import com.lkpower.pis.data.api.LearnDocApi
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.FaultInfo
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Inject

class FaultInfoRespository @Inject constructor() {

    // 故障信息查询列表
    fun getFaultInfoList(searchInfo: String, pageInfo: String, tokenKey: String): Observable<BaseResp<ListResult<FaultInfo>>> {
        return RetrofitFactory.instance.create(FaultInfoApi::class.java).getFaultInfoList(searchInfo, pageInfo, tokenKey)
    }

    // 获取故障信息详细信息
    fun getFaultInfoModel(faultId: String, tokenKey: String): Observable<BaseResp<FaultInfo>> {
        return RetrofitFactory.instance.create(FaultInfoApi::class.java).getFaultInfoModel(faultId, tokenKey)
    }

    // 故障反馈
    fun addFaultInfo(tokenKey: String, faultInfo: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(FaultInfoApi::class.java).addFaultInfo(tokenKey, faultInfo)
    }

    // 故障修复确认
    fun addFaultInfoConfirm(tokenKey: String, confirmInfo: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(FaultInfoApi::class.java).addFaultInfoConfirm(tokenKey, confirmInfo)
    }


}