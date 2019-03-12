package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.PublishInfo
import com.lkpower.pis.presenter.view.PublishDetailView
import com.lkpower.pis.service.impl.PublishServiceImpl
import javax.inject.Inject

class PublishDetailPresenter @Inject constructor() : BasePresenter<PublishDetailView>() {

    @Inject
    lateinit var publishService: PublishServiceImpl

    fun gePublishModel(id: String, deviceId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        publishService.getPublishInfoModel(id, deviceId, tokenKey).execute(object : BaseSubscriber<PublishInfo>(mView) {
            override fun onNext(t: PublishInfo) {
                super.onNext(t)
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }
}