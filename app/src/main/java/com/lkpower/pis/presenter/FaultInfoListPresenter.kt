package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.*
import com.lkpower.pis.presenter.view.*
import com.lkpower.pis.service.*
import com.lkpower.pis.service.impl.DrivingInfoServiceImpl
import javax.inject.Inject

class FaultInfoListPresenter @Inject constructor() : BasePresenter<FaultInfoListView>() {
    @Inject
    lateinit var faultInfoService:FaultInfoService

    fun getFaultInfoList(searchInfo: String, pageInfo: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        faultInfoService.getFaultInfoList(searchInfo, pageInfo, tokenKey).execute(object : BaseSubscriber<ListResult<FaultInfo>>(mView) {
            override fun onNext(t: ListResult<FaultInfo>) {
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}