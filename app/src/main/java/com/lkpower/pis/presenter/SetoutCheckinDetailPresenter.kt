package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutCheckIn
import com.lkpower.pis.presenter.view.SetoutCheckinDetailView
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class SetoutCheckinDetailPresenter @Inject constructor() : BasePresenter<SetoutCheckinDetailView>() {
    @Inject
    lateinit var setoutService: SetoutService

    fun getSetoutCheckinDetail(instanceId: String, taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.getSetoutCheckInDetail(instanceId, taskId, tokenKey).execute(object : BaseSubscriber<SetoutCheckIn>(mView) {
            override fun onNext(t: SetoutCheckIn) {
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }

    fun setOutCheckin(taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.setOutCheckIn(taskId, tokenKey).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.setOutResult(t)
            }
        }, lifecycleProvider)
    }
}