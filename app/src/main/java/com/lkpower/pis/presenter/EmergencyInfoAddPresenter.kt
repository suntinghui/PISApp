package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.EmergencyInfo
import com.lkpower.pis.presenter.view.DrivingInfoUploadView
import com.lkpower.pis.presenter.view.EmergencyInfoAddView
import com.lkpower.pis.service.DrivingInfoService
import com.lkpower.pis.service.EmergencyInfoService
import javax.inject.Inject

class EmergencyInfoAddPresenter @Inject constructor() : BasePresenter<EmergencyInfoAddView>() {

    @Inject
    lateinit var emergencyInfoService: EmergencyInfoService


    fun addEmergencyInfo(info:String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        emergencyInfoService.addEmergencyInfo(info, tokenKey).execute(object : BaseSubscriber<CommonReturn>(mView) {
            override fun onNext(t: CommonReturn) {
                super.onNext(t)
                mView.onUploadDetailResult(t)
            }
        }, lifecycleProvider)
    }


}