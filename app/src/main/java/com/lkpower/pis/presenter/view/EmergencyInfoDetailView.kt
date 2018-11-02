package com.lkpower.pis.presenter.view

import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.*

interface EmergencyInfoDetailView : BaseView {

    fun onGetDetailResult(result: EmergencyInfo)

    fun onGetAttListResult(result: List<AttModel>)
}