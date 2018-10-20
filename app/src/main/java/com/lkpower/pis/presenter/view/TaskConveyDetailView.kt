package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.TaskConveyDetail

interface TaskConveyDetailView : BaseView {

    fun onGetDetailResult(result: TaskConveyDetail)

    fun onConfirmResult(result: String)
}