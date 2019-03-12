package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
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
                super.onNext(t)
                mView.onUploadDetailResult(t)
            }
        }, lifecycleProvider)
    }


}