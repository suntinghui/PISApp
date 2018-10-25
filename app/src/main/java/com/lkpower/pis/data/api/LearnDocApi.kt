package com.lkpower.pis.data.api

import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.protocol.SetoffCheckIn
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/*
 学习文件
 */
interface LearnDocApi {

    // 学习文件查询列表
    @FormUrlEncoded
    @POST("InfoManager.ashx?Commond=GetLearnDocList")
    fun getLearnDocList(@Field("searchInfo") searchInfo: String,
                             @Field("pageInfo") pageInfo: String,
                             @Field("tokenKey") tokenKey: String): Observable<BaseResp<ListResult<LearnDoc>>>

    // 学习文件详细信息
    @FormUrlEncoded
    @POST("InfoManager.ashx?Commond=GetLearnDocModel")
    fun getLearnDocModel(@Field("DocId") docId: String,
                         @Field("tokenKey") tokenKey: String): Observable<BaseResp<LearnDoc>>

    // 学习文件设置已读
    @FormUrlEncoded
    @POST("InfoManager.ashx?Commond=SetLearnDocRead")
    fun setLearnDocRead(@Field("DocId") docId: String,
                        @Field("tokenKey") tokenKey: String): Observable<BaseResp<String>>


}