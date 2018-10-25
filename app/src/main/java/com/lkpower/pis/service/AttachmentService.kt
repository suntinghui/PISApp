package com.lkpower.pis.service

import com.lkpower.base.data.net.RetrofitFactory
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.api.PublishApi
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.protocol.PublishInfo
import io.reactivex.Observable

interface AttachmentService {
    /*
    附件对应业务模块类型
    行车巡检任务图片：1
    行车信息图片：2
    其他模块附件信息：3
     */

    // 查询附件列表
    fun getAttList(busId: String, ATTBusModule: String, tokenKey: String): Observable<List<AttModel>>

}