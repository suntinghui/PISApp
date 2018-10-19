package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutAlcoholTestInfo
import com.lkpower.pis.data.protocol.SetoutCheckInInfo
import com.lkpower.pis.presenter.view.SetoutAlcoholTestListView
import com.lkpower.pis.presenter.view.SetoutCheckinListView
import com.lkpower.pis.service.TasktanceService
import javax.inject.Inject

class SetoutAlcoholTestListPresenter @Inject constructor() : BasePresenter<SetoutAlcoholTestListView>() {
    @Inject
    lateinit var tasktanceService: TasktanceService

    fun getSetoutAlcoholTestList(instanceId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        tasktanceService.getSetoutAlcoholTestList(instanceId, tokenKey).execute(object : BaseSubscriber<List<SetoutAlcoholTestInfo>>(mView) {
            override fun onNext(t: List<SetoutAlcoholTestInfo>) {
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}