package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.presenter.view.FeedbackView
import com.lkpower.pis.service.SettingService
import javax.inject.Inject

class FeedbackPresenter @Inject constructor() : BasePresenter<FeedbackView>() {

    @Inject
    lateinit var settingService: SettingService

    fun addFeebback(feedbackInfo: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        settingService.addFeebback(feedbackInfo, tokenKey).execute(object : BaseSubscriber<CommonReturn>(mView) {
            override fun onNext(t: CommonReturn) {
                mView.onAddResult(t)
            }
        }, lifecycleProvider)

    }


}