package com.lkpower.pis.data.respository

import com.lkpower.base.data.net.RetrofitFactory
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.api.UserApi
import com.lkpower.pis.data.protocol.UserInfo
import com.lkpower.pis.data.protocol.XJ_LCFCInfo
import io.reactivex.Observable
import javax.inject.Inject

class UserRespository @Inject constructor() {

    fun login(loginName:String, loginPwd:String, deviceId:String): Observable<BaseResp<UserInfo>> {
        return RetrofitFactory.instance.create(UserApi::class.java).login(loginName, loginPwd, deviceId)
    }

    fun getLCFCInstance(empId:String, tokenKey:String) : Observable<BaseResp<List<XJ_LCFCInfo>>> {
        return RetrofitFactory.instance.create(UserApi::class.java).getLCFCInstance(empId, tokenKey)
    }

}