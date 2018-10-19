package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutAlcoholTestInfo
import com.lkpower.pis.presenter.view.SetoutAlcoholTestDetailView
import com.lkpower.pis.service.TasktanceService
import javax.inject.Inject

class SetoutAlcoholTestDetailPresenter @Inject constructor() : BasePresenter<SetoutAlcoholTestDetailView>() {

    @Inject
    lateinit var tasktanceService: TasktanceService

    fun getSetoutAlcoholTestDetail(instanceId: String, taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        tasktanceService.getSetoutAlcoholTestDetail(instanceId, taskId, tokenKey).execute(object : BaseSubscriber<SetoutAlcoholTestInfo>(mView) {
            override fun onNext(t: SetoutAlcoholTestInfo) {
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }

    fun setOutAlcoholTest(taskId: String, result: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        tasktanceService.setOutAlcoholTest(taskId, result, tokenKey).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.setOutResult(t)
            }
        }, lifecycleProvider)
    }
}