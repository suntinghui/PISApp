package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.SetoffInfo
import com.lkpower.pis.data.protocol.SetoutInfo
import com.lkpower.pis.presenter.view.SetoffDetailView
import com.lkpower.pis.presenter.view.SetoutDetailView
import com.lkpower.pis.service.SetoffService
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class SetoffDetailPresenter @Inject constructor() : BasePresenter<SetoffDetailView>() {
    @Inject
    lateinit var setoffService: SetoffService

    fun getSetoff(instanceId: String, taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoffService.getSetoff(instanceId, taskId, tokenKey).execute(object : BaseSubscriber<SetoffInfo>(mView) {
            override fun onNext(t: SetoffInfo) {
                super.onNext(t)
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }

    fun setoffConfirm(taskId: String, TaskPlace: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoffService.setoffConfirm(taskId, TaskPlace, tokenKey).execute(object : BaseSubscriber<CommonReturn>(mView) {
            override fun onNext(t: CommonReturn) {
                super.onNext(t)
                mView.setOffResult(t)
            }

            override fun onComplete() {
                super.onComplete()

                mView.setOffComplete()
            }
        }, lifecycleProvider)
    }
}