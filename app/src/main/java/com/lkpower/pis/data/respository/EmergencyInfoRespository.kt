package com.lkpower.pis.data.respository

import com.lkpower.pis.data.net.RetrofitFactory
import com.lkpower.pis.data.protocol.BaseResp
import com.lkpower.pis.data.api.EmergencyInfoApi
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.EmergencyInfo
import com.lkpower.pis.data.protocol.ListResult
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Inject

class EmergencyInfoRespository @Inject constructor() {

    // 应急信息查询列表
    fun getEmergencyInfoList(searchInfo: String, pageInfo: String, tokenKey: String): Observable<BaseResp<ListResult<EmergencyInfo>>> {
        return RetrofitFactory.instance.create(EmergencyInfoApi::class.java).getEmergencyInfoList(searchInfo, pageInfo, tokenKey)
    }

    // 应急处置信息明细
    fun getEmergencyInfoModel(emInfoId: String, tokenKey: String): Observable<BaseResp<EmergencyInfo>> {
        return RetrofitFactory.instance.create(EmergencyInfoApi::class.java).getEmergencyInfoModel(emInfoId, tokenKey)
    }

    // 应急处置反馈
    fun addEmergencyInfo(info:String, tokenKey: String): Observable<BaseResp<CommonReturn>> {
        return RetrofitFactory.instance.create(EmergencyInfoApi::class.java).addEmergencyInfo(info, tokenKey)
    }
}