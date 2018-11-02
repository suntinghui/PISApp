package com.lkpower.pis.service.impl

import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.ext.convert
import com.lkpower.pis.ext.convertBoolean
import com.lkpower.pis.data.respository.AttachmentRespository
import com.lkpower.pis.service.AttachmentService
import io.reactivex.Observable
import javax.inject.Inject

class AttachmentServiceImpl @Inject constructor() : AttachmentService {

    @Inject
    lateinit var attachmentRespository: AttachmentRespository

    override fun getAttList(busId: String, attType: String, tokenKey: String): Observable<List<AttModel>> {
        return attachmentRespository.getAttList(busId, attType, tokenKey).convert()
    }

    override fun deleteFile(attId: String, attType: String, tokenKey: String): Observable<Boolean> {
        return attachmentRespository.deleteFile(attId, attType, tokenKey).convertBoolean()
    }



}