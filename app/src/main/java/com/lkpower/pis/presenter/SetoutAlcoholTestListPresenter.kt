package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.SetoutAlcoholTest
import com.lkpower.pis.presenter.view.SetoutAlcoholTestListView
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class SetoutAlcoholTestListPresenter @Inject constructor() : BasePresenter<SetoutAlcoholTestListView>() {
    @Inject
    lateinit var setoutService: SetoutService

    fun getSetoutAlcoholTestList(instanceId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        setoutService.getSetoutAlcoholTestList(instanceId, tokenKey).execute(object : BaseSubscriber<List<SetoutAlcoholTest>>(mView) {
            override fun onNext(t: List<SetoutAlcoholTest>) {
                super.onNext(t)
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}