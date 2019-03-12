package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutAlcoholTest
import com.lkpower.pis.presenter.view.SetoutAlcoholTestDetailView
import com.lkpower.pis.service.AttachmentService
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class SetoutAlcoholTestDetailPresenter @Inject constructor() : BasePresenter<SetoutAlcoholTestDetailView>() {

    @Inject
    lateinit var setoutService: SetoutService

    @Inject
    lateinit var attachmentService: AttachmentService

    fun getSetoutAlcoholTestDetail(instanceId: String, taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.getSetoutAlcoholTestDetail(instanceId, taskId, tokenKey).execute(object : BaseSubscriber<SetoutAlcoholTest>(mView) {
            override fun onNext(t: SetoutAlcoholTest) {
                super.onNext(t)
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }

    fun setOutAlcoholTest(taskId: String, result: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.setOutAlcoholTest(taskId, result, tokenKey).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                super.onNext(t)
                mView.setOutResult(t)
            }
        }, lifecycleProvider)
    }

    fun getAttList(busId: String, attType: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        attachmentService.getAttList(busId, attType, tokenKey).execute(object : BaseSubscriber<List<AttModel>>(mView) {
            override fun onNext(t: List<AttModel>) {
                super.onNext(t)
                mView.onGetAttListResult(t)
            }
        }, lifecycleProvider)
    }
}