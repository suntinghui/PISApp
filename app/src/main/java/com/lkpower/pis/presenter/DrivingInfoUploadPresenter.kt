package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.presenter.view.DrivingInfoUploadView
import com.lkpower.pis.service.DrivingInfoService
import javax.inject.Inject

class DrivingInfoUploadPresenter @Inject constructor() : BasePresenter<DrivingInfoUploadView>() {

    @Inject
    lateinit var drivingInfoService: DrivingInfoService


    fun updateDrivingInfo(instanceId: String, remark:String,uuid:String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        drivingInfoService.updateDrivingInfo(instanceId, remark,uuid, tokenKey).execute(object : BaseSubscriber<CommonReturn>(mView) {
            override fun onNext(t: CommonReturn) {
                mView.onUploadDetailResult(t)
            }
        }, lifecycleProvider)
    }


}