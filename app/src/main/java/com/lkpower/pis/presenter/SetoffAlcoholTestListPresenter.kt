package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoffAlcoholTest
import com.lkpower.pis.data.protocol.SetoutAlcoholTest
import com.lkpower.pis.presenter.view.SetoffAlcoholTestListView
import com.lkpower.pis.presenter.view.SetoutAlcoholTestListView
import com.lkpower.pis.service.SetoffService
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class SetoffAlcoholTestListPresenter @Inject constructor() : BasePresenter<SetoffAlcoholTestListView>() {
    @Inject
    lateinit var setoffService: SetoffService

    fun getSetoffAlcoholTestList(instanceId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoffService.getSetoffAlcoholTestList(instanceId, tokenKey).execute(object : BaseSubscriber<List<SetoffAlcoholTest>>(mView) {
            override fun onNext(t: List<SetoffAlcoholTest>) {
                super.onNext(t)
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}