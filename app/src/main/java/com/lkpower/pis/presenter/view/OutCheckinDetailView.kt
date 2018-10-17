package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.OutCheckInInfo

interface OutCheckinDetailView : BaseView {

    fun onGetDetailResult(result: OutCheckInInfo)
}