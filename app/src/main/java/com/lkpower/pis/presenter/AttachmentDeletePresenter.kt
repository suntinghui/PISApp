package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.presenter.view.AttachmentDeleteView
import com.lkpower.pis.presenter.view.DrivingInfoUploadView
import com.lkpower.pis.service.AttachmentService
import com.lkpower.pis.service.DrivingInfoService
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
                mView.onDeleteResult(t)
            }
        }, lifecycleProvider)
    }


}