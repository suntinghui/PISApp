package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.protocol.SetoutCheckIn
import com.lkpower.pis.data.protocol.XJ_CZSL
import com.lkpower.pis.presenter.view.InspectionStationListView
import com.lkpower.pis.presenter.view.LearnDocListView
import com.lkpower.pis.presenter.view.SetoutCheckinListView
import com.lkpower.pis.service.InspectionService
import com.lkpower.pis.service.LearnDocService
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class InspectionStationListPresenter @Inject constructor() : BasePresenter<InspectionStationListView>() {

    @Inject
    lateinit var inspectionService: InspectionService

    fun getSiteList(instanceId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        inspectionService.getSiteList(instanceId, tokenKey).execute(object : BaseSubscriber<List<XJ_CZSL>>(mView) {
            override fun onNext(t: List<XJ_CZSL>) {
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}