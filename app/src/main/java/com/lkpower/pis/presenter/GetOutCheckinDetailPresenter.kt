package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.OutCheckInInfo
import com.lkpower.pis.presenter.view.OutCheckinDetailView
import com.lkpower.pis.presenter.view.OutCheckinListView
import com.lkpower.pis.service.TasktanceService
import javax.inject.Inject

class GetOutCheckinDetailPresenter @Inject constructor() : BasePresenter<OutCheckinDetailView>() {
    @Inject
    lateinit var tasktanceService: TasktanceService

    fun getOutCheckinDetail(instanceId: String, taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        tasktanceService.getOutCheckInDetail(instanceId, taskId, tokenKey).execute(object : BaseSubscriber<OutCheckInInfo>(mView) {
            override fun onNext(t: OutCheckInInfo) {
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }
}