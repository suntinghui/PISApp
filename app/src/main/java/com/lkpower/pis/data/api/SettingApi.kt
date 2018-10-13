package com.lkpower.pis.data.api

import com.lkpower.base.common.BaseConstant
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.protocol.FirAppInfo
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SettingApi {

    // 查询FIR上的最新的版本信息
    @GET("latest/${ BaseConstant.FIR_APP_ID}")
    fun queryLatest(@Query("api_token")token:String): Call<FirAppInfo>


    // 安装应用第一步，获取 download_token
    @GET("${ BaseConstant.FIR_APP_ID}/download_token")
    fun getDownloadToken(@Query("api_token")token:String): Call<FirAppInfo>

}