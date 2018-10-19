package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutCheckInInfo
import com.lkpower.pis.data.protocol.SetoutInfo
import com.lkpower.pis.presenter.view.SetoutCheckinListView
import com.lkpower.pis.presenter.view.SetoutListView
import com.lkpower.pis.service.TasktanceService
import javax.inject.Inject

class SetoutListPresenter @Inject constructor() : BasePresenter<SetoutListView>() {
    @Inject
    lateinit var tasktanceService: TasktanceService

    fun getSetoutList(instanceId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        tasktanceService.getSetoutList(instanceId, tokenKey).execute(object : BaseSubscriber<List<SetoutInfo>>(mView) {
            override fun onNext(t: List<SetoutInfo>) {
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}