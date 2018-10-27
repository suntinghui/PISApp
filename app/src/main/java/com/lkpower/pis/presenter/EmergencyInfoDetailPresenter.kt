package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
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


}