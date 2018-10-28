package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.SetoffInfo
import com.lkpower.pis.data.protocol.SetoutInfo
import com.lkpower.pis.presenter.view.SetoffDetailView
import com.lkpower.pis.presenter.view.SetoutDetailView
import com.lkpower.pis.service.SetoffService
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class SetoffDetailPresenter @Inject constructor() : BasePresenter<SetoffDetailView>() {
    @Inject
    lateinit var setoffService: SetoffService

    fun getSetoff(instanceId: String, taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoffService.getSetoff(instanceId, taskId, tokenKey).execute(object : BaseSubscriber<SetoffInfo>(mView) {
            override fun onNext(t: SetoffInfo) {
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }

    fun setoffConfirm(taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoffService.setoffConfirm(taskId, tokenKey).execute(object : BaseSubscriber<CommonReturn>(mView) {
            override fun onNext(t: CommonReturn) {
                mView.setOffResult(t)
            }
        }, lifecycleProvider)
    }
}