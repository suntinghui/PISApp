package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.*
import com.lkpower.pis.presenter.view.InspectionStationListView
import com.lkpower.pis.presenter.view.InspectionTaskListView
import com.lkpower.pis.presenter.view.LearnDocListView
import com.lkpower.pis.presenter.view.SetoutCheckinListView
import com.lkpower.pis.service.InspectionService
import com.lkpower.pis.service.LearnDocService
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class InspectionTaskListPresenter @Inject constructor() : BasePresenter<InspectionTaskListView>() {

    @Inject
    lateinit var inspectionService: InspectionService

    fun getXJTaskList(instanceId: String, sitId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        inspectionService.getXJTaskList(instanceId, sitId, tokenKey).execute(object : BaseSubscriber<List<MissionStateInfo>>(mView) {
            override fun onNext(t: List<MissionStateInfo>) {
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}