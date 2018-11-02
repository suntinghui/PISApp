package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.protocol.SetoutCheckIn
import com.lkpower.pis.presenter.view.LearnDocListView
import com.lkpower.pis.presenter.view.SetoutCheckinListView
import com.lkpower.pis.service.LearnDocService
import com.lkpower.pis.service.SetoutService
import javax.inject.Inject

class LearnDocListPresenter @Inject constructor() : BasePresenter<LearnDocListView>() {
    @Inject
    lateinit var learnDocService: LearnDocService

    fun getSetoutCheckinList(searchInfo: String, pageInfo: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        learnDocService.getLearnDocList(searchInfo, pageInfo, tokenKey).execute(object : BaseSubscriber<ListResult<LearnDoc>>(mView) {
            override fun onNext(t: ListResult<LearnDoc>) {
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }
}