package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.presenter.view.DrivingInfoUploadView
import com.lkpower.pis.presenter.view.EmergencyInfoAddView
import com.lkpower.pis.service.DrivingInfoService
import com.lkpower.pis.service.EmergencyInfoService
import javax.inject.Inject

class EmergencyInfoAddPresenter @Inject constructor() : BasePresenter<EmergencyInfoAddView>() {

    @Inject
    lateinit var emergencyInfoService: EmergencyInfoService


    fun addEmergencyInfo(instanceId: String, remark:String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        emergencyInfoService.addEmergencyInfo(instanceId, remark, tokenKey).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onUploadDetailResult(t)
            }
        }, lifecycleProvider)
    }


}