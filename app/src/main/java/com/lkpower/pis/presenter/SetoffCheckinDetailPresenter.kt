package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoffCheckIn
import com.lkpower.pis.data.protocol.SetoutCheckIn
import com.lkpower.pis.presenter.view.SetoffCheckinDetailView
import com.lkpower.pis.presenter.view.SetoutCheckinDetailView
import com.lkpower.pis.service.SetoffService
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class SetoffCheckinDetailPresenter @Inject constructor() : BasePresenter<SetoffCheckinDetailView>() {
    @Inject
    lateinit var setoffService: SetoffService

    fun getSetoffCheckIn(instanceId: String, taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoffService.getSetoffCheckIn(instanceId, taskId, tokenKey).execute(object : BaseSubscriber<SetoffCheckIn>(mView) {
            override fun onNext(t: SetoffCheckIn) {
                super.onNext(t)
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }

    fun setOffCheckIn(taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoffService.setOffCheckIn(taskId, tokenKey).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                super.onNext(t)
                mView.setOutResult(t)
            }
        }, lifecycleProvider)
    }
}