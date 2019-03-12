package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutInfo
import com.lkpower.pis.presenter.view.SetoutListView
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class SetoutListPresenter @Inject constructor() : BasePresenter<SetoutListView>() {
    @Inject
    lateinit var setoutService: SetoutService

    fun getSetoutList(instanceId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.getSetoutList(instanceId, tokenKey).execute(object : BaseSubscriber<List<SetoutInfo>>(mView) {
            override fun onNext(t: List<SetoutInfo>) {
                super.onNext(t)
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}