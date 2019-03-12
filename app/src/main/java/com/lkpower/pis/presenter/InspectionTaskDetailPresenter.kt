package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.MissionStateInfo
import com.lkpower.pis.presenter.view.InspectionTaskDetailView
import com.lkpower.pis.service.AttachmentService
import com.lkpower.pis.service.InspectionService
import javax.inject.Inject

class InspectionTaskDetailPresenter @Inject constructor() : BasePresenter<InspectionTaskDetailView>() {

    @Inject
    lateinit var inspectionService: InspectionService

    @Inject
    lateinit var attachmentService: AttachmentService

    fun getXJTaskModel(taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        inspectionService.getXJTaskModel(taskId, tokenKey).execute(object : BaseSubscriber<MissionStateInfo>(mView) {
            override fun onNext(t: MissionStateInfo) {
                super.onNext(t)
                mView.onGetDetailResult(t)
            }
        }, lifecycleProvider)
    }

    fun updateMissionInfoExt(taskId: String, state:String, remark:String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        inspectionService.updateMissionInfoExt(taskId, state, remark, tokenKey).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                super.onNext(t)
                mView.onUpdateMissionInfo(t)
            }
        }, lifecycleProvider)
    }

    fun getAttList(busId: String, attType: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        attachmentService.getAttList(busId, attType, tokenKey).execute(object : BaseSubscriber<List<AttModel>>(mView) {
            override fun onNext(t: List<AttModel>) {
                super.onNext(t)
                mView.onGetAttListResult(t)
            }
        }, lifecycleProvider)
    }

}