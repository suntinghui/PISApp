package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.view.AttachmentDeleteView
import com.lkpower.pis.service.AttachmentService
import javax.inject.Inject

class AttachmentDeletePresenter @Inject constructor() : BasePresenter<AttachmentDeleteView>() {

    @Inject
    lateinit var attachmentService: AttachmentService


    fun deleteFile(attId: String, attType: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        attachmentService.deleteFile(attId, attType, tokenKey).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                super.onNext(t)
                mView.onDeleteResult(t)
            }
        }, lifecycleProvider)
    }


}