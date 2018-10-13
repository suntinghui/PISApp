package com.lkpower.pis.data.respository

import com.lkpower.base.data.net.RetrofitFactory
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.api.UserApi
import com.lkpower.pis.data.protocol.LoginReq
import com.lkpower.pis.data.protocol.UserInfo
import io.reactivex.Observable
import javax.inject.Inject

class UserRespository @Inject constructor() {

    fun login(loginName:String, loginPwd:String, deviceId:String): Observable<BaseResp<UserInfo>> {
        return RetrofitFactory.instance.create(UserApi::class.java).login(LoginReq(loginName, loginPwd, deviceId))
    }

}