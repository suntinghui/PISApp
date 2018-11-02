package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.CommonReturn
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

    fun setOutConfirm(taskId: String, TaskPlace: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.setoutConfirm(taskId, TaskPlace, tokenKey).execute(object : BaseSubscriber<CommonReturn>(mView) {
            override fun onNext(t: CommonReturn) {
                mView.setOutResult(t)
            }
        }, lifecycleProvider)
    }
}