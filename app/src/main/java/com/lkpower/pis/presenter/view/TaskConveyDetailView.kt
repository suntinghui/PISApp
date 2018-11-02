package com.lkpower.pis.presenter.view

import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.TaskConveyDetail

interface TaskConveyDetailView : BaseView {

    fun onGetDetailResult(result: TaskConveyDetail)

    fun onConfirmResult(result: Boolean)

    fun onGetAttListResult(result: List<AttModel>)
}