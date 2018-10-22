package com.lkpower.pis.service

import com.lkpower.base.data.net.RetrofitFactory
import com.lkpower.base.data.protocol.BaseResp
import com.lkpower.pis.data.api.UserApi
import com.lkpower.pis.data.protocol.UserInfo
import com.lkpower.pis.data.protocol.XJ_LCFC
import io.reactivex.Observable

interface UserService {

    // 登录
    fun login(loginName: String, loginPwd: String, deviceId: String): Observable<UserInfo>

    // 注销
    fun loginOff(tokenKey: String): Observable<String>

    // 修改密码
    fun changePwd(loginName: String, loginPwd: String, newPwd: String, tokenKey: String): Observable<String>

    /**
     * 获取发车实例
     */
    fun getLCFCInstance(empId: String, tokenKey: String): Observable<List<XJ_LCFC>>
}