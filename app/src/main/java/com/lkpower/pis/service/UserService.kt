package com.lkpower.pis.service

import com.lkpower.pis.data.protocol.UserInfo
import com.lkpower.pis.data.protocol.XJ_LCFCInfo
import io.reactivex.Observable

interface UserService {

    // 登录
    fun login(loginName: String, loginPwd: String, deviceId: String): Observable<UserInfo>

    /**
     * 获取发车实例
     */
    fun getLCFCInstance(empId: String, tokenKey: String): Observable<List<XJ_LCFCInfo>>
}