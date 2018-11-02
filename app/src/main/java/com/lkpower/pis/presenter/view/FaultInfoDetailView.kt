package com.lkpower.pis.presenter.view

import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.*

interface FaultInfoDetailView : BaseView {

    fun onGetDetailResult(result: FaultInfo)

    fun onConfirmResult(result:CommonReturn)

    fun onGetAttListResult(result: List<AttModel>)
}