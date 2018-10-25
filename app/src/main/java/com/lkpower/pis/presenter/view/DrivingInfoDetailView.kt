package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.SetoutCheckIn

interface DrivingInfoDetailView : BaseView {

    fun onGetDetailResult(result: DrivingInfo)
}