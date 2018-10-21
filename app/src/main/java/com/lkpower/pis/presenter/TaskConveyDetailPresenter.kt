package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.TaskConveyDetail
import com.lkpower.pis.presenter.view.TaskConveyDetailView
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class TaskConveyDetailPresenter @Inject constructor() : BasePresenter<TaskConveyDetailView>() {
    @Inject
    lateinit var setoutService: SetoutService

    fun getTaskConveyDetail(conveyDetailId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.getTaskConveyDetail(conveyDetailId, tokenKey).execute(object : BaseSubscriber<TaskConveyDetail>(mView) {
            override fun onNext(t: TaskConveyDetail) {
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }

    fun taskRiskItemConfirm(itemId: String, feedBack: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.taskRiskItemConfirm(itemId, feedBack, tokenKey).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onConfirmResult(t)
            }
        }, lifecycleProvider)
    }
}