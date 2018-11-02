package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutConfirmProj
import com.lkpower.pis.presenter.view.SetoutConfirmProjListView
import com.lkpower.pis.service.AttachmentService
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class SetoutGroupConfirmProjListPresenter @Inject constructor() : BasePresenter<SetoutConfirmProjListView>() {

    @Inject
    lateinit var setoutService: SetoutService

    @Inject
    lateinit var attachmentService: AttachmentService

    fun getSetOutConfirmProjList(instanceId: String, groupTaskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.getSetOutConfirmProjList(instanceId, groupTaskId, tokenKey).execute(object : BaseSubscriber<List<SetoutConfirmProj>>(mView) {
            override fun onNext(t: List<SetoutConfirmProj>) {
                mView.onGetProjListResult(t)
            }
        }, lifecycleProvider)
    }

    fun setoutConfirmProj(taskId: String, confirmRemark: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.setoutConfirmProj(taskId,confirmRemark, tokenKey).execute(object : BaseSubscriber<Boolean>(mView) {
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