package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.presenter.view.DrivingInfoDetailView
import com.lkpower.pis.service.AttachmentService
import com.lkpower.pis.service.DrivingInfoService
import javax.inject.Inject

class DrivingInfoDetailPresenter @Inject constructor() : BasePresenter<DrivingInfoDetailView>() {

    @Inject
    lateinit var drivingInfoService: DrivingInfoService

    @Inject
    lateinit var attachmentService: AttachmentService


    fun getDrivingInfoModel(id: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        drivingInfoService.getDrivingInfoModel(id, tokenKey).execute(object : BaseSubscriber<DrivingInfo>(mView) {
            override fun onNext(t: DrivingInfo) {
                super.onNext(t)
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }

    fun getAttList(busId: String, attType: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        attachmentService.getAttList(busId, attType, tokenKey).execute(object : BaseSubscriber<List<AttModel>>(mView) {
            override fun onNext(t: List<AttModel>) {
                mView.onGetAttListResult(t)
            }
        }, lifecycleProvider)
    }


}