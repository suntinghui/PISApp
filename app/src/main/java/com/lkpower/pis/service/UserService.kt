package com.lkpower.pis.service

import com.lkpower.pis.data.protocol.UserInfo
import io.reactivex.Observable

interface UserService {

    fun login(loginName:String, loginPwd:String, deviceId:String):Observable<UserInfo>
}