package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
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
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }
}