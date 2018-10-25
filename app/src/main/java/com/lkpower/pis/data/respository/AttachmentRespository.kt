package com.lkpower.pis.data.respository

import com.lkpower.base.data.net.RetrofitFactory
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.api.AttachmentApi
import com.lkpower.pis.data.api.DrivingInfoApi
import com.lkpower.pis.data.api.LearnDocApi
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Inject

class AttachmentRespository @Inject constructor() {

    // 行车信息查询列表
    fun getAttList(busId: String, attType: String, tokenKey:String): Observable<BaseResp<List<AttModel>>> {
        return RetrofitFactory.instance.create(AttachmentApi::class.java).getAttList(busId, attType, tokenKey)
    }
}