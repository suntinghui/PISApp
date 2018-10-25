package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.MissionStateInfo
import com.lkpower.pis.presenter.view.InspectionTaskDetailView
import com.lkpower.pis.service.InspectionService
import javax.inject.Inject

class InspectionTaskDetailPresenter @Inject constructor() : BasePresenter<InspectionTaskDetailView>() {

    @Inject
    lateinit var inspectionService: InspectionService

    fun getXJTaskModel(taskId: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        inspectionService.getXJTaskModel(taskId, tokenKey).execute(object : BaseSubscriber<MissionStateInfo>(mView) {
            override fun onNext(t: MissionStateInfo) {
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
                mView.onUpdateMissionInfo(t)
            }
        }, lifecycleProvider)
    }
}