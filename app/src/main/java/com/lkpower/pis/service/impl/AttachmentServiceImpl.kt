package com.lkpower.pis.service.impl

import com.lkpower.base.ext.convert
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.data.respository.AttachmentRespository
import com.lkpower.pis.service.AttachmentService
import io.reactivex.Observable
import javax.inject.Inject

class AttachmentServiceImpl @Inject constructor() : AttachmentService {

    @Inject
    lateinit var attachmentRespository: AttachmentRespository

    override fun getAttList(busId: String, attType: String,tokenKey: String): Observable<List<AttModel>> {
        return attachmentRespository.getAttList(busId, attType, tokenKey).convert()
    }


}