package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutCheckInInfo
import com.lkpower.pis.data.protocol.SetoutGroupTask
import com.lkpower.pis.presenter.view.SetoutCheckinListView
import com.lkpower.pis.presenter.view.SetoutGroupTaskListView
import com.lkpower.pis.service.TasktanceService
import javax.inject.Inject

class SetoutGroupTaskListPresenter @Inject constructor() : BasePresenter<SetoutGroupTaskListView>() {
    @Inject
    lateinit var tasktanceService: TasktanceService

    fun getSetoutCheckinList(instanceId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        tasktanceService.getSetoutGroupTaskList(instanceId, tokenKey).execute(object : BaseSubscriber<List<SetoutGroupTask>>(mView) {
            override fun onNext(t: List<SetoutGroupTask>) {
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}