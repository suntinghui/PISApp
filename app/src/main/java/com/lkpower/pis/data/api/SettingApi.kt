package com.lkpower.pis.data.api

import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.data.protocol.BaseResp
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.FirAppInfo
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface SettingApi {

    // 查询FIR上的最新的版本信息
    @GET("latest/${BaseConstant.FIR_APP_ID}")
    fun queryLatest(@Query("api_token") token: String): Call<FirAppInfo>


    // 安装应用第一步，获取 download_token
    @GET("${BaseConstant.FIR_APP_ID}/download_token")
    fun getDownloadToken(@Query("api_token") token: String): Call<FirAppInfo>

    // 意见反馈
    @FormUrlEncoded
    @POST("SetConfig.ashx?Commond=AddFeedBack")
    fun addFeedback(@Field("confirmInfo") confirmInfo: String,
                    @Field("tokenKey") tokenKey: String): Observable<BaseResp<CommonReturn>>


}