package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutCheckInInfo
import com.lkpower.pis.presenter.view.SetoutCheckinListView
import com.lkpower.pis.service.TasktanceService
import javax.inject.Inject

class SetoutCheckInListPresenter @Inject constructor() : BasePresenter<SetoutCheckinListView>() {
    @Inject
    lateinit var tasktanceService: TasktanceService

    fun getSetoutCheckinList(instanceId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        tasktanceService.getSetoutCheckInList(instanceId, tokenKey).execute(object : BaseSubscriber<List<SetoutCheckInInfo>>(mView) {
            override fun onNext(t: List<SetoutCheckInInfo>) {
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}