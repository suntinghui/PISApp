package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutCheckInInfo
import com.lkpower.pis.data.protocol.SetoutInfo
import com.lkpower.pis.presenter.view.SetoutCheckinDetailView
import com.lkpower.pis.presenter.view.SetoutDetailView
import com.lkpower.pis.service.TasktanceService
import javax.inject.Inject

class SetoutDetailPresenter @Inject constructor() : BasePresenter<SetoutDetailView>() {
    @Inject
    lateinit var tasktanceService: TasktanceService

    fun getSetoutDetail(instanceId: String, taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        tasktanceService.getSetout(instanceId, taskId, tokenKey).execute(object : BaseSubscriber<SetoutInfo>(mView) {
            override fun onNext(t: SetoutInfo) {
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }

    fun setOutConfirm(taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        tasktanceService.setoutConfirm(taskId, tokenKey).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.setOutResult(t)
            }
        }, lifecycleProvider)
    }
}