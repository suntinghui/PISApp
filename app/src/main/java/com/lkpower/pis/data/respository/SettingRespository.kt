package com.lkpower.pis.data.respository

import com.lkpower.base.data.net.RetrofitFactory
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.api.SettingApi
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Inject


class SettingRespository @Inject constructor() {

    // 意见反馈
    fun addFeedback(searchInfo: String, tokenKey: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(SettingApi::class.java).addFeedback(searchInfo, tokenKey)
    }


}