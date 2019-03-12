package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoffInfo
import com.lkpower.pis.data.protocol.SetoutInfo
import com.lkpower.pis.presenter.view.SetoffListView
import com.lkpower.pis.presenter.view.SetoutListView
import com.lkpower.pis.service.SetoffService
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class SetoffListPresenter @Inject constructor() : BasePresenter<SetoffListView>() {
    @Inject
    lateinit var setoffService: SetoffService

    fun getSetoffList(instanceId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoffService.getSetoffList(instanceId, tokenKey).execute(object : BaseSubscriber<List<SetoffInfo>>(mView) {
            override fun onNext(t: List<SetoffInfo>) {
                super.onNext(t)
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}