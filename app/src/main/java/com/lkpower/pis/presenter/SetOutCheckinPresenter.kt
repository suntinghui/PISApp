package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.OutCheckInInfo
import com.lkpower.pis.presenter.view.OutCheckinListView
import com.lkpower.pis.presenter.view.SetOutCheckinView
import com.lkpower.pis.service.TasktanceService
import javax.inject.Inject

class SetOutCheckinPresenter @Inject constructor() : BasePresenter<SetOutCheckinView>() {
    @Inject
    lateinit var tasktanceService: TasktanceService

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