package com.lkpower.pis.presenter

import com.lkpower.pis.rx.BaseSubscriber
import com.lkpower.pis.ext.execute
import com.lkpower.pis.presenter.BasePresenter
import com.lkpower.pis.data.protocol.*
import com.lkpower.pis.presenter.view.*
import com.lkpower.pis.service.*
import com.lkpower.pis.service.impl.DrivingInfoServiceImpl
import javax.inject.Inject

class FaultInfoListPresenter @Inject constructor() : BasePresenter<FaultInfoListView>() {
    @Inject
    lateinit var faultInfoService:FaultInfoService

    fun getFaultInfoList(searchInfo: String, pageInfo: String, tokenKey: String) {
        if (!checkNetWork())
            return

        mView.showLoading()

        faultInfoService.getFaultInfoList(searchInfo, pageInfo, tokenKey).execute(object : BaseSubscriber<ListResult<FaultInfo>>(mView) {
            override fun onNext(t: ListResult<FaultInfo>) {
                super.onNext(t)
                mView.onGetListResult(t)
            }
        }, lifecycleProvider)
    }

    fun getFailPartList(keyword: String) {
        if (!checkNetWork())
            return

        faultInfoService.getFailPartList(keyword).execute(object : BaseSubscriber<List<SysDic>>(mView) {
            override fun onNext(t: List<SysDic>) {
                super.onNext(t)
                mView.onFailPartResult(t)
            }
        }, lifecycleProvider)
    }

    fun getFaultTypeList(relParentId: String, keyword: String="") {
        if (!checkNetWork())
            return

        faultInfoService.getFaultTypeList(relParentId, keyword).execute(object : BaseSubscriber<List<SysDic>>(mView) {
            override fun onNext(t: List<SysDic>) {
                super.onNext(t)
                mView.onFaultTypeResult(t)
            }
        }, lifecycleProvider)
    }
}