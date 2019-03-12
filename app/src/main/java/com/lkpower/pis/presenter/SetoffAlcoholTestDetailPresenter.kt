package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoffAlcoholTest
import com.lkpower.pis.data.protocol.SetoutAlcoholTest
import com.lkpower.pis.presenter.view.SetoffAlcoholTestDetailView
import com.lkpower.pis.presenter.view.SetoutAlcoholTestDetailView
import com.lkpower.pis.service.AttachmentService
import com.lkpower.pis.service.SetoffService
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class SetoffAlcoholTestDetailPresenter @Inject constructor() : BasePresenter<SetoffAlcoholTestDetailView>() {

    @Inject
    lateinit var setoffService: SetoffService

    @Inject
    lateinit var attachmentService: AttachmentService

    fun getSetOffAlcoholTest(instanceId: String, taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoffService.getSetOffAlcoholTest(instanceId, taskId, tokenKey).execute(object : BaseSubscriber<SetoffAlcoholTest>(mView) {
            override fun onNext(t: SetoffAlcoholTest) {
                super.onNext(t)
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }

    fun setoffAlcoholTest(taskId: String, result: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoffService.setoffAlcoholTest(taskId, result, tokenKey).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                super.onNext(t)
                mView.setOffResult(t)
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