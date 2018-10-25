package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutConfirmProj
import com.lkpower.pis.presenter.view.SetoutConfirmProjListView
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class SetoutGroupConfirmProjListPresenter @Inject constructor() : BasePresenter<SetoutConfirmProjListView>() {
    @Inject
    lateinit var setoutService: SetoutService

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

    fun setoutConfirmProj(taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.setoutConfirmProj(taskId, tokenKey).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onConfirmResult(t)
            }
        }, lifecycleProvider)
    }
}