package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.UserInfo
import com.lkpower.pis.presenter.view.LoginView
import com.lkpower.pis.service.UserService
import javax.inject.Inject

class LoginPresenter @Inject constructor(): BasePresenter<LoginView>() {
    @Inject
    lateinit var userService:UserService

    fun login(userName:String, pwd:String, deviceId:String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        userService.login(userName, pwd, deviceId).execute(object:BaseSubscriber<UserInfo>(mView){
            override fun onNext(t: UserInfo) {
                mView.onLoginResult(t)
            }
        }, lifecycleProvider)
    }
}