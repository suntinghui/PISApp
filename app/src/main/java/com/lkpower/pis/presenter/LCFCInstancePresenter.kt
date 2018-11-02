package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.XJ_LCFC
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

        userService.getLCFCInstance(empId, tokenKey).execute(object : BaseSubscriber<List<XJ_LCFC>>(mView) {
            override fun onNext(t: List<XJ_LCFC>) {
                mView.onGetLCFCInstanceResult(t)
            }
        }, lifecycleProvider)
    }
}