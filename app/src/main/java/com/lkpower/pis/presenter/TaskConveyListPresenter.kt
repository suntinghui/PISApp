package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.TaskConveyDetail
import com.lkpower.pis.presenter.view.TaskConveyListView
import com.lkpower.pis.service.TasktanceService
import javax.inject.Inject

class TaskConveyListPresenter @Inject constructor() : BasePresenter<TaskConveyListView>() {

    @Inject
    lateinit var tasktanceService: TasktanceService

    fun getTaskConveyList(instanceId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        tasktanceService.getTaskConveyList(instanceId, tokenKey).execute(object : BaseSubscriber<List<TaskConveyDetail>>(mView) {
            override fun onNext(t: List<TaskConveyDetail>) {
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}