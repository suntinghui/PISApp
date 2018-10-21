package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutCheckInInfo
import com.lkpower.pis.data.protocol.SetoutConfirmProj
import com.lkpower.pis.data.protocol.SetoutGroupTask
import com.lkpower.pis.presenter.view.SetoutCheckinListView
import com.lkpower.pis.presenter.view.SetoutConfirmProjListView
import com.lkpower.pis.presenter.view.SetoutGroupTaskListView
import com.lkpower.pis.service.TasktanceService
import javax.inject.Inject

class SetoutGroupConfirmProjListPresenter @Inject constructor() : BasePresenter<SetoutConfirmProjListView>() {
    @Inject
    lateinit var tasktanceService: TasktanceService

    fun getSetOutConfirmProjList(instanceId: String, groupTaskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        tasktanceService.getSetOutConfirmProjList(instanceId, groupTaskId, tokenKey).execute(object : BaseSubscriber<List<SetoutConfirmProj>>(mView) {
            override fun onNext(t: List<SetoutConfirmProj>) {
                mView.onGetProjListResult(t)
            }
        }, lifecycleProvider)
    }

    fun setoutConfirmProj(taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        tasktanceService.setoutConfirmProj(taskId, tokenKey).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onConfirmResult(t)
            }
        }, lifecycleProvider)
    }
}