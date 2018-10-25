package com.lkpower.pis.data.respository

import com.lkpower.base.data.net.RetrofitFactory
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.api.DrivingInfoApi
import com.lkpower.pis.data.api.LearnDocApi
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Inject

class DrivingInfoRespository @Inject constructor() {

    // 行车信息查询列表
    fun getDrivingInfoList(searchInfo: String, pageInfo: String, tokenKey: String): Observable<BaseResp<ListResult<DrivingInfo>>> {
        return RetrofitFactory.instance.create(DrivingInfoApi::class.java).getDrivingInfoList(searchInfo, pageInfo, tokenKey)
    }

    // 获取行车信息详细信息
    fun getDrivingInfoModel(id: String, tokenKey: String): Observable<BaseResp<DrivingInfo>> {
        return RetrofitFactory.instance.create(DrivingInfoApi::class.java).getDrivingInfoModel(id, tokenKey)
    }

    // 行车信息上报
    fun updateDrivingInfo(instanceId: String, remark: String, tokenKey: String): Observable<BaseResp<Boolean>> {
        return RetrofitFactory.instance.create(DrivingInfoApi::class.java).updateDrivingInfo(instanceId, remark, tokenKey)
    }
}