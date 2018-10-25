package com.lkpower.pis.data.api

import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.ListResult
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/*
行车信息
 */
interface AttachmentApi {

    // 获取附件列表
    @FormUrlEncoded
    @POST("ATTManager.ashx?Commond=GetAttList")
    fun getAttList(@Field("busId") busId: String,
                   @Field("ATTBusModule") attType: String,
                   @Field("tokenKey") tokenKey: String): Observable<BaseResp<List<AttModel>>>

}