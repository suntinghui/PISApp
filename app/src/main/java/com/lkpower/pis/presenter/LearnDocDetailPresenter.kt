package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.SetoutAlcoholTest
import com.lkpower.pis.presenter.view.LearnDocDetailView
import com.lkpower.pis.presenter.view.SetoutAlcoholTestDetailView
import com.lkpower.pis.service.LearnDocService
import com.lkpower.pis.service.SetoutService
import com.lkpower.pis.service.impl.LearnDocServiceImpl
import javax.inject.Inject

class LearnDocDetailPresenter @Inject constructor() : BasePresenter<LearnDocDetailView>() {

    @Inject
    lateinit var learnDocService: LearnDocServiceImpl

    fun getLearnDocModel(docId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        learnDocService.getLearnDocModel(docId, tokenKey).execute(object : BaseSubscriber<LearnDoc>(mView) {
            override fun onNext(t: LearnDoc) {
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }

    fun setLearnDocRead(docId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        learnDocService.setLearnDocRead(docId, tokenKey).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.setOutResult(t)
            }
        }, lifecycleProvider)
    }
}