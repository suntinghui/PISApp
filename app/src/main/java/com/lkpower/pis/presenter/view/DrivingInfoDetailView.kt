package com.lkpower.pis.presenter.view

import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.DrivingInfo

interface DrivingInfoDetailView : BaseView {

    fun onGetDetailResult(result: DrivingInfo)

    fun onGetAttListResult(result: List<AttModel>)
}