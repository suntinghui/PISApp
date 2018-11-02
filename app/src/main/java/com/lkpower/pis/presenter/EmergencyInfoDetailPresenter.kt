package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.*
import com.lkpower.pis.presenter.view.DrivingInfoDetailView
import com.lkpower.pis.presenter.view.EmergencyInfoDetailView
import com.lkpower.pis.presenter.view.LearnDocDetailView
import com.lkpower.pis.presenter.view.SetoutAlcoholTestDetailView
import com.lkpower.pis.service.*
import com.lkpower.pis.service.impl.LearnDocServiceImpl
import javax.inject.Inject

class EmergencyInfoDetailPresenter @Inject constructor() : BasePresenter<EmergencyInfoDetailView>() {

    @Inject
    lateinit var emergencyInfoService: EmergencyInfoService

    @Inject
    lateinit var attachmentService: AttachmentService


    fun getEmergencyInfoModel(emInfoId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        emergencyInfoService.getEmergencyInfoModel(emInfoId, tokenKey).execute(object : BaseSubscriber<EmergencyInfo>(mView) {
            override fun onNext(t: EmergencyInfo) {
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