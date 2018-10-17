package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.OutCheckInInfo

interface SetOutCheckinView : BaseView {

    fun setOutResult(result: String)
}