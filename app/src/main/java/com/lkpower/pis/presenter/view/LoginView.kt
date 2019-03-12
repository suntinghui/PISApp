package com.lkpower.pis.presenter.view

import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.UserInfo

interface LoginView : BaseView {

    fun onLoginResult(result: UserInfo)

    fun onLoginComplete()
}