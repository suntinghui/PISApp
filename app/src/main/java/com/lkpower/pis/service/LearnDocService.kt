package com.lkpower.pis.service

import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import io.reactivex.Observable

interface LearnDocService {

    // 学习文件查询列表
    fun getLearnDocList(searchInfo: String, pageInfo: String, tokenKey: String): Observable<ListResult<LearnDoc>>

    // 学习文件详细信息
    fun getLearnDocModel(docId: String, tokenKey: String): Observable<LearnDoc>

    // 学习文件设置已读
    fun setLearnDocRead(docId: String, tokenKey: String): Observable<Boolean>
}