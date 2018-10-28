package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.data.protocol.AttModel
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.*
import com.lkpower.pis.presenter.view.*
import com.lkpower.pis.service.*
import com.lkpower.pis.service.impl.LearnDocServiceImpl
import javax.inject.Inject

class FaultInfoDetailPresenter @Inject constructor() : BasePresenter<FaultInfoDetailView>() {

    @Inject
    lateinit var faultInfoService: FaultInfoService

    @Inject
    lateinit var attachmentService: AttachmentService


    fun getFaultInfoModel(faultId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        faultInfoService.getFaultInfoModel(faultId, tokenKey).execute(object : BaseSubscriber<FaultInfo>(mView) {
            override fun onNext(t: FaultInfo) {
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }

    // 故障修复确认
    fun addFaultInfoConfirm(confirmInfo: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        faultInfoService.addFaultInfoConfirm(tokenKey, confirmInfo).execute(object : BaseSubscriber<CommonReturn>(mView) {
            override fun onNext(t: CommonReturn) {
                mView.onConfirmResult(t)
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