package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutCheckInInfo
import com.lkpower.pis.presenter.view.SetoutCheckinDetailView
import com.lkpower.pis.service.TasktanceService
import javax.inject.Inject

class SetoutCheckinDetailPresenter @Inject constructor() : BasePresenter<SetoutCheckinDetailView>() {
    @Inject
    lateinit var tasktanceService: TasktanceService

    fun getSetoutCheckinDetail(instanceId: String, taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        tasktanceService.getSetoutCheckInDetail(instanceId, taskId, tokenKey).execute(object : BaseSubscriber<SetoutCheckInInfo>(mView) {
            override fun onNext(t: SetoutCheckInInfo) {
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }

    fun setOutCheckin(taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        tasktanceService.setOutCheckIn(taskId, tokenKey).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.setOutResult(t)
            }
        }, lifecycleProvider)
    }
}