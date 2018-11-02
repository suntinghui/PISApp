package com.lkpower.pis.service.impl

import com.lkpower.pis.ext.convert
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.protocol.PublishInfo
import com.lkpower.pis.data.respository.LearnDocRespository
import com.lkpower.pis.data.respository.PublishRespository
import com.lkpower.pis.service.LearnDocService
import com.lkpower.pis.service.PublishService
import io.reactivex.Observable
import javax.inject.Inject

class PublishServiceImpl @Inject constructor() : PublishService {

    @Inject
    lateinit var publishRespository: PublishRespository

    override fun getPublishInfoList(searchInfo: String, pageInfo: String, tokenKey: String): Observable<ListResult<PublishInfo>> {
        return publishRespository.getPublishInfoList(searchInfo, pageInfo, tokenKey).convert()
    }

    override fun getPublishInfoModel(id: String, deviceId: String, tokenKey: String): Observable<PublishInfo> {
        return publishRespository.getPublishInfoModel(id, deviceId, tokenKey).convert()
    }


}