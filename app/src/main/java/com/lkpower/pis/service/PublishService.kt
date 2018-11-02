package com.lkpower.pis.service

import com.lkpower.pis.data.net.RetrofitFactory
import com.lkpower.pis.data.protocol.BaseResp
import com.lkpower.pis.data.api.PublishApi
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.protocol.PublishInfo
import io.reactivex.Observable

interface PublishService {

    // 发信息查询列表
    fun getPublishInfoList(searchInfo: String, pageInfo: String, tokenKey: String): Observable<ListResult<PublishInfo>>

    // 段发信息明细
    fun getPublishInfoModel(id: String, deviceId: String, tokenKey: String): Observable<PublishInfo>
}