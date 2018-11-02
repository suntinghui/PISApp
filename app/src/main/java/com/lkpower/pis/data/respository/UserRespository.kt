package com.lkpower.pis.data.respository

import com.lkpower.pis.data.net.RetrofitFactory
import com.lkpower.pis.data.protocol.BaseResp
import com.lkpower.pis.data.api.UserApi
import com.lkpower.pis.data.protocol.UserInfo
import com.lkpower.pis.data.protocol.XJ_LCFC
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Inject

class UserRespository @Inject constructor() {

    // 登录
    fun login(loginName: String, loginPwd: String, deviceId: String): Observable<BaseResp<UserInfo>> {
        return RetrofitFactory.instance.create(UserApi::class.java).login(loginName, loginPwd, deviceId)
    }


    // 注销
    fun loginOff(tokenKey: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UserApi::class.java).loginOff(tokenKey)
    }

    // 修改密码
    fun changePwd(loginName: String, loginPwd: String, newPwd: String, tokenKey: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UserApi::class.java).changePwd(loginName, loginPwd, newPwd, tokenKey)
    }

    fun getLCFCInstance(empId: String, tokenKey: String): Observable<BaseResp<List<XJ_LCFC>>> {
        return RetrofitFactory.instance.create(UserApi::class.java).getLCFCInstance(empId, tokenKey)
    }

}