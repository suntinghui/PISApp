package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutCheckIn
import com.lkpower.pis.presenter.view.SetoutCheckinListView
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class SetoutCheckInListPresenter @Inject constructor() : BasePresenter<SetoutCheckinListView>() {
    @Inject
    lateinit var setoutService: SetoutService

    fun getSetoutCheckinList(instanceId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.getSetoutCheckInList(instanceId, tokenKey).execute(object : BaseSubscriber<List<SetoutCheckIn>>(mView) {
            override fun onNext(t: List<SetoutCheckIn>) {
                super.onNext(t)
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}