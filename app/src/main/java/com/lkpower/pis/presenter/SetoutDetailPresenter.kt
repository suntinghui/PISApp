package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutInfo
import com.lkpower.pis.presenter.view.SetoutDetailView
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class SetoutDetailPresenter @Inject constructor() : BasePresenter<SetoutDetailView>() {
    @Inject
    lateinit var setoutService: SetoutService

    fun getSetoutDetail(instanceId: String, taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.getSetout(instanceId, taskId, tokenKey).execute(object : BaseSubscriber<SetoutInfo>(mView) {
            override fun onNext(t: SetoutInfo) {
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }

    fun setOutConfirm(taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.setoutConfirm(taskId, tokenKey).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.setOutResult(t)
            }
        }, lifecycleProvider)
    }
}