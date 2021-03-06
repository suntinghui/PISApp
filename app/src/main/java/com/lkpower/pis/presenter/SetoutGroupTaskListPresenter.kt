package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutGroupTask
import com.lkpower.pis.presenter.view.SetoutGroupTaskListView
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class SetoutGroupTaskListPresenter @Inject constructor() : BasePresenter<SetoutGroupTaskListView>() {
    @Inject
    lateinit var setoutService: SetoutService

    fun getSetoutCheckinList(instanceId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.getSetoutGroupTaskList(instanceId, tokenKey).execute(object : BaseSubscriber<List<SetoutGroupTask>>(mView) {
            override fun onNext(t: List<SetoutGroupTask>) {
                super.onNext(t)
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}