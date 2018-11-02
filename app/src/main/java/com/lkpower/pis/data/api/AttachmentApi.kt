package com.lkpower.pis.data.api

import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.data.protocol.BaseResp
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

/*
附件
 */
interface AttachmentApi {

    // 获取附件列表
    @FormUrlEncoded
    @POST("ATTManager.ashx?Commond=GetAttList")
    fun getAttList(@Field("busId") busId: String,
                   @Field("ATTBusModule") attType: String,
                   @Field("tokenKey") tokenKey: String): Observable<BaseResp<List<AttModel>>>

    // 文件删除
    @FormUrlEncoded
    @POST("ATTManager.ashx?Commond=Del")
    fun deleteFile(@Field("AttId") attId: String,
                   @Field("ATTBusModule") attType: String,
                   @Field("tokenKey") tokenKey: String): Observable<BaseResp<Boolean>>

}