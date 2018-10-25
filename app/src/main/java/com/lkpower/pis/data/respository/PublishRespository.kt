package com.lkpower.pis.data.respository

import com.lkpower.base.data.net.RetrofitFactory
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.api.DrivingInfoApi
import com.lkpower.pis.data.api.LearnDocApi
import com.lkpower.pis.data.api.PublishApi
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.protocol.PublishInfo
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Inject

class PublishRespository @Inject constructor() {

    // 发信息查询列表
    fun getPublishInfoList(searchInfo: String, pageInfo: String, tokenKey: String): Observable<BaseResp<ListResult<PublishInfo>>> {
        return RetrofitFactory.instance.create(PublishApi::class.java).getPublishInfoList(searchInfo, pageInfo, tokenKey)
    }

    // 段发信息明细
    fun getPublishInfoModel(id: String, deviceId: String, tokenKey: String): Observable<BaseResp<PublishInfo>> {
        return RetrofitFactory.instance.create(PublishApi::class.java).getPublishInfoModel(id, deviceId, tokenKey)
    }
}