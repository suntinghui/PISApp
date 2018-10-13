package com.lkpower.pis.data.api

import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.protocol.LoginReq
import com.lkpower.pis.data.protocol.UserInfo
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("userCenter/login")
    fun login(@Body req: LoginReq): Observable<BaseResp<UserInfo>>
}