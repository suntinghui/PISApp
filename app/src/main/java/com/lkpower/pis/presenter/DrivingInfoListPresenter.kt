package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.protocol.SetoutCheckIn
import com.lkpower.pis.presenter.view.DrivingInfoListView
import com.lkpower.pis.presenter.view.LearnDocListView
import com.lkpower.pis.presenter.view.SetoutCheckinListView
import com.lkpower.pis.service.DrivingInfoService
import com.lkpower.pis.service.LearnDocService
import com.lkpower.pis.service.SetoutService
import com.lkpower.pis.service.impl.DrivingInfoServiceImpl
import javax.inject.Inject

class DrivingInfoListPresenter @Inject constructor() : BasePresenter<DrivingInfoListView>() {
    @Inject
    lateinit var drivingInfoService: DrivingInfoService

    fun getDrivingInfoList(searchInfo: String, pageInfo: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        drivingInfoService.getDrivingInfoList(searchInfo, pageInfo, tokenKey).execute(object : BaseSubscriber<ListResult<DrivingInfo>>(mView) {
            override fun onNext(t: ListResult<DrivingInfo>) {
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}