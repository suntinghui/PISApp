package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.protocol.PublishInfo
import com.lkpower.pis.data.protocol.SetoutCheckIn
import com.lkpower.pis.presenter.view.LearnDocListView
import com.lkpower.pis.presenter.view.PublishListView
import com.lkpower.pis.presenter.view.SetoutCheckinListView
import com.lkpower.pis.service.LearnDocService
import com.lkpower.pis.service.PublishService
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class PublishListPresenter @Inject constructor() : BasePresenter<PublishListView>() {
    @Inject
    lateinit var publishService: PublishService

    fun getPublishInfoList(searchInfo: String, pageInfo: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        publishService.getPublishInfoList(searchInfo, pageInfo, tokenKey).execute(object : BaseSubscriber<ListResult<PublishInfo>>(mView) {
            override fun onNext(t: ListResult<PublishInfo>) {
                super.onNext(t)
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}