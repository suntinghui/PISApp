package com.lkpower.pis.data.respository

import com.lkpower.base.data.net.RetrofitFactory
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.api.LearnDocApi
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import io.reactivex.Observable
import javax.inject.Inject

class LearnDocRespository @Inject constructor() {

    // 学习文件查询列表
    fun getLearnDocList(searchInfo: String, pageInfo: String, tokenKey: String): Observable<BaseResp<ListResult<LearnDoc>>> {
        return RetrofitFactory.instance.create(LearnDocApi::class.java).getLearnDocList(searchInfo, pageInfo, tokenKey)
    }

    // 学习文件详细信息
    fun getLearnDocModel(docId: String, tokenKey: String): Observable<BaseResp<LearnDoc>> {
        return RetrofitFactory.instance.create(LearnDocApi::class.java).getLearnDocModel(docId, tokenKey)
    }

    // 学习文件设置已读
    fun setLearnDocRead(docId: String, tokenKey: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(LearnDocApi::class.java).setLearnDocRead(docId, tokenKey)
    }
}