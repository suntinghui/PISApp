package com.lkpower.pis.data.respository

import com.lkpower.pis.data.net.RetrofitFactory
import com.lkpower.pis.data.protocol.BaseResp
import com.lkpower.pis.data.api.SettingApi
import com.lkpower.pis.data.protocol.CommonReturn
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Inject


class SettingRespository @Inject constructor() {

    // 意见反馈
    fun addFeedback(feedbackInfo: String, tokenKey: String): Observable<BaseResp<CommonReturn>> {
        return RetrofitFactory.instance.create(SettingApi::class.java).addFeedback(feedbackInfo, tokenKey)
    }


}