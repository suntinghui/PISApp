package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.*
import com.lkpower.pis.presenter.view.DrivingInfoListView
import com.lkpower.pis.presenter.view.EmergencyInfoListView
import com.lkpower.pis.presenter.view.LearnDocListView
import com.lkpower.pis.presenter.view.SetoutCheckinListView
import com.lkpower.pis.service.DrivingInfoService
import com.lkpower.pis.service.EmergencyInfoService
import com.lkpower.pis.service.LearnDocService
import com.lkpower.pis.service.SetoutService
import com.lkpower.pis.service.impl.DrivingInfoServiceImpl
import javax.inject.Inject

class EmergencyInfoListPresenter @Inject constructor() : BasePresenter<EmergencyInfoListView>() {
    @Inject
    lateinit var emergencyInfoService: EmergencyInfoService

    fun getEmergencyInfoList(searchInfo: String, pageInfo: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        emergencyInfoService.getEmergencyInfoList(searchInfo, pageInfo, tokenKey).execute(object : BaseSubscriber<ListResult<EmergencyInfo>>(mView) {
            override fun onNext(t: ListResult<EmergencyInfo>) {
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}