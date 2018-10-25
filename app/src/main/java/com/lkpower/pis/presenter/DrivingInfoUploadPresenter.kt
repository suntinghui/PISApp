package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.presenter.view.DrivingInfoUploadView
import com.lkpower.pis.service.DrivingInfoService
import javax.inject.Inject

class DrivingInfoUploadPresenter @Inject constructor() : BasePresenter<DrivingInfoUploadView>() {

    @Inject
    lateinit var drivingInfoService: DrivingInfoService


    fun getDrivingInfoModel(instanceId: String, remark:String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        drivingInfoService.updateDrivingInfo(instanceId, remark, tokenKey).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onUploadResult(t)
            }
        }, lifecycleProvider)
    }


}