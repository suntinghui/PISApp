package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.SetoutAlcoholTest
import com.lkpower.pis.presenter.view.DrivingInfoDetailView
import com.lkpower.pis.presenter.view.LearnDocDetailView
import com.lkpower.pis.presenter.view.SetoutAlcoholTestDetailView
import com.lkpower.pis.service.AttachmentService
import com.lkpower.pis.service.DrivingInfoService
import com.lkpower.pis.service.LearnDocService
import com.lkpower.pis.service.SetoutService
import com.lkpower.pis.service.impl.LearnDocServiceImpl
import javax.inject.Inject

class DrivingInfoDetailPresenter @Inject constructor() : BasePresenter<DrivingInfoDetailView>() {

    @Inject
    lateinit var drivingInfoService: DrivingInfoService


    fun getDrivingInfoModel(id: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        drivingInfoService.getDrivingInfoModel(id, tokenKey).execute(object : BaseSubscriber<DrivingInfo>(mView) {
            override fun onNext(t: DrivingInfo) {
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }


}