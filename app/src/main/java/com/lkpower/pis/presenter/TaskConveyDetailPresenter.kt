package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.TaskConveyDetail
import com.lkpower.pis.presenter.view.TaskConveyDetailView
import com.lkpower.pis.service.AttachmentService
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class TaskConveyDetailPresenter @Inject constructor() : BasePresenter<TaskConveyDetailView>() {
    @Inject
    lateinit var setoutService: SetoutService

    @Inject
    lateinit var attachmentService: AttachmentService

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

        setoutService.taskRiskItemConfirm(itemId, feedBack, tokenKey).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
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