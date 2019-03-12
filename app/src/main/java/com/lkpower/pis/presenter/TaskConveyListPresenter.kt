package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.TaskConveyDetail
import com.lkpower.pis.presenter.view.TaskConveyListView
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class TaskConveyListPresenter @Inject constructor() : BasePresenter<TaskConveyListView>() {

    @Inject
    lateinit var setoutService: SetoutService

    fun getTaskConveyList(instanceId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.getTaskConveyList(instanceId, tokenKey).execute(object : BaseSubscriber<List<TaskConveyDetail>>(mView) {
            override fun onNext(t: List<TaskConveyDetail>) {
                super.onNext(t)
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}