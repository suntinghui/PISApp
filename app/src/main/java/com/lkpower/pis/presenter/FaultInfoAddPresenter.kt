package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.SysDic
import com.lkpower.pis.presenter.view.FaultInfoAddView
import com.lkpower.pis.service.FaultInfoService
import javax.inject.Inject

// 故障反馈
class FaultInfoAddPresenter @Inject constructor() : BasePresenter<FaultInfoAddView>() {

    @Inject
    lateinit var faultInfoService: FaultInfoService


    fun addFaultInfo(tokenKey: String, info: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        faultInfoService.addFaultInfo(tokenKey, info).execute(object : BaseSubscriber<CommonReturn>(mView) {
            override fun onNext(t: CommonReturn) {
                mView.onAddDetailResult(t)
            }
        }, lifecycleProvider)
    }

    fun getFailPartList(keyword: String) {
        if (!checkNetWork())
            return

        faultInfoService.getFailPartList(keyword).execute(object : BaseSubscriber<List<SysDic>>(mView) {
            override fun onNext(t: List<SysDic>) {
                mView.onFailPartResult(t)
            }
        }, lifecycleProvider)
    }

    fun getFaultTypeList(relParentId: String, keyword: String="") {
        if (!checkNetWork())
            return

        faultInfoService.getFaultTypeList(relParentId, keyword).execute(object : BaseSubscriber<List<SysDic>>(mView) {
            override fun onNext(t: List<SysDic>) {
                mView.onFaultTypeResult(t)
            }
        }, lifecycleProvider)
    }


}