package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.data.protocol.AttModel
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.presenter.view.LearnDocDetailView
import com.lkpower.pis.service.AttachmentService
import com.lkpower.pis.service.LearnDocService
import javax.inject.Inject

class LearnDocDetailPresenter @Inject constructor() : BasePresenter<LearnDocDetailView>() {

    @Inject
    lateinit var learnDocService: LearnDocService

    @Inject
    lateinit var attachmentService: AttachmentService

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

    /*
    附件对应业务模块类型
    行车巡检任务图片：1
    行车信息图片：2
    其他模块附件信息：3
     */
    fun getAttList(busId: String, attType: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        attachmentService.getAttList(busId, attType, tokenKey).execute(object : BaseSubscriber<List<AttModel>>(mView) {
            override fun onNext(t: List<AttModel>) {
                mView.onGetAttListResult(t)
            }
        }, lifecycleProvider)
    }
}