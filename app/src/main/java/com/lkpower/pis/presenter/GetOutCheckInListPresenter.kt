package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.OutCheckInInfo
import com.lkpower.pis.presenter.view.OutCheckinListView
import com.lkpower.pis.service.TasktanceService
import javax.inject.Inject

class GetOutCheckInListPresenter @Inject constructor() : BasePresenter<OutCheckinListView>() {
    @Inject
    lateinit var tasktanceService: TasktanceService

    fun getOutCheckinList(instanceId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        tasktanceService.getOutCheckInList(instanceId, tokenKey).execute(object : BaseSubscriber<List<OutCheckInInfo>>(mView) {
            override fun onNext(t: List<OutCheckInInfo>) {
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}