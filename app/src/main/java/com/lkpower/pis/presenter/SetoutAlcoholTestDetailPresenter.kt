package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutAlcoholTest
import com.lkpower.pis.presenter.view.SetoutAlcoholTestDetailView
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class SetoutAlcoholTestDetailPresenter @Inject constructor() : BasePresenter<SetoutAlcoholTestDetailView>() {

    @Inject
    lateinit var setoutService: SetoutService

    fun getSetoutAlcoholTestDetail(instanceId: String, taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.getSetoutAlcoholTestDetail(instanceId, taskId, tokenKey).execute(object : BaseSubscriber<SetoutAlcoholTest>(mView) {
            override fun onNext(t: SetoutAlcoholTest) {
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
                mView.setOutResult(t)
            }
        }, lifecycleProvider)
    }
}