package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoffCheckIn
import com.lkpower.pis.data.protocol.SetoutCheckIn
import com.lkpower.pis.presenter.view.SetoffCheckinListView
import com.lkpower.pis.presenter.view.SetoutCheckinListView
import com.lkpower.pis.service.SetoffService
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class SetoffCheckInListPresenter @Inject constructor() : BasePresenter<SetoffCheckinListView>() {
    @Inject
    lateinit var setoffService: SetoffService

    fun getSetoffCheckinList(instanceId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoffService.getSetoffCheckInList(instanceId, tokenKey).execute(object : BaseSubscriber<List<SetoffCheckIn>>(mView) {
            override fun onNext(t: List<SetoffCheckIn>) {
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}