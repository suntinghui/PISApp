package com.lkpower.pis.service.impl

import com.lkpower.base.ext.convert
import com.lkpower.base.ext.convertBoolean
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.respository.LearnDocRespository
import com.lkpower.pis.service.LearnDocService
import io.reactivex.Observable
import javax.inject.Inject

class LearnDocServiceImpl @Inject constructor() : LearnDocService {

    @Inject
    lateinit var docRespository: LearnDocRespository

    override fun getLearnDocList(searchInfo: String, pageInfo: String, tokenKey: String): Observable<ListResult<LearnDoc>> {
        return docRespository.getLearnDocList(searchInfo, pageInfo, tokenKey).convert()
    }

    override fun getLearnDocModel(docId: String, tokenKey: String): Observable<LearnDoc> {
        return docRespository.getLearnDocModel(docId, tokenKey).convert()
    }

    override fun setLearnDocRead(docId: String, tokenKey: String): Observable<Boolean> {
        return docRespository.setLearnDocRead(docId, tokenKey).convertBoolean()
    }
}