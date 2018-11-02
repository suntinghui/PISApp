package com.lkpower.pis.data.respository

import com.lkpower.pis.data.net.RetrofitFactory
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.data.protocol.BaseResp
import com.lkpower.pis.data.api.AttachmentApi
import io.reactivex.Observable
import javax.inject.Inject

class AttachmentRespository @Inject constructor() {

    // 附件列表查询
    fun getAttList(busId: String, attType: String, tokenKey: String): Observable<BaseResp<List<AttModel>>> {
        return RetrofitFactory.instance.create(AttachmentApi::class.java).getAttList(busId, attType, tokenKey)
    }

    // 文件删除
    fun deleteFile(attId:String, attType: String, tokenKey: String):Observable<BaseResp<Boolean>> {
        return RetrofitFactory.instance.create(AttachmentApi::class.java).deleteFile(attId, attType, tokenKey)
    }
}