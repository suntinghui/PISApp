package com.lkpower.pis.data.api

import com.lkpower.base.data.protocol.AttModel
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.protocol.SysDic
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

/*
公用模块
 */
interface CommonApi {

    // 获取字典类型数据
    @FormUrlEncoded
    @POST("ComManager.ashx?Commond=GetDicList")
    fun getDicList(@Field("dicType") dicType: String,
                   @Field("RelParentId") relParentId: String,
                   @Field("KeyWord") keyword: String): Observable<BaseResp<List<SysDic>>>


}