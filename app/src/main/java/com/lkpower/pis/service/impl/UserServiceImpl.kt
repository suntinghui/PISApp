package com.lkpower.pis.service.impl

import com.lkpower.base.ext.convert
import com.lkpower.pis.data.protocol.UserInfo
import com.lkpower.pis.data.respository.SettingRespository
import com.lkpower.pis.data.respository.UserRespository
import com.lkpower.pis.service.UserService
import io.reactivex.Observable
import javax.inject.Inject

class UserServiceImpl @Inject constructor() : UserService {

    @Inject
    lateinit var respository: UserRespository

    override fun login(loginName: String, loginPwd: String, deviceId: String): Observable<UserInfo> {
        return respository.login(loginName, loginPwd, deviceId).convert()
    }
}