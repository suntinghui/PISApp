package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.UserInfo
import com.lkpower.pis.presenter.view.LoginView
import com.lkpower.pis.service.UserService
import javax.inject.Inject

class LoginPresenter @Inject constructor() : BasePresenter<LoginView>() {
    @Inject
    lateinit var userService: UserService

    fun login(userName: String, pwd: String, deviceId: String, APPVersion: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        userService.login(userName, pwd, deviceId, APPVersion).execute(object : BaseSubscriber<UserInfo>(mView) {
            override fun onNext(t: UserInfo) {
                super.onNext(t)

                mView.onLoginResult(t)
            }

            override fun onComplete() {
                super.onComplete()

                mView.onLoginComplete()
            }
        }, lifecycleProvider)
    }
}