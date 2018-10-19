package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.XJ_LCFCInfo
import com.lkpower.pis.presenter.view.LCFCInstanceView
import com.lkpower.pis.service.UserService
import javax.inject.Inject

class LCFCInstancePresenter @Inject constructor() : BasePresenter<LCFCInstanceView>() {
    @Inject
    lateinit var userService: UserService

    fun getLCFCInstance(empId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        userService.getLCFCInstance(empId, tokenKey).execute(object : BaseSubscriber<List<XJ_LCFCInfo>>(mView) {
            override fun onNext(t: List<XJ_LCFCInfo>) {
                mView.onGetLCFCInstanceResult(t)
            }
        }, lifecycleProvider)
    }
}